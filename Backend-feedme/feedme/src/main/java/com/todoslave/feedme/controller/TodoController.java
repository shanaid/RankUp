package com.todoslave.feedme.controller;

import com.todoslave.feedme.DTO.TodoCalendarResponseDTO;
import com.todoslave.feedme.DTO.TodoCreateRequestDTO;
import com.todoslave.feedme.DTO.TodoDailyRequestDTO;
import com.todoslave.feedme.DTO.TodoMainResponseDTO;
import com.todoslave.feedme.DTO.TodoModifyRequestDTO;
import com.todoslave.feedme.DTO.TodoRequestDTO;
import com.todoslave.feedme.DTO.TodoResponseDTO;
import com.todoslave.feedme.service.TodoService;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todos")
public class TodoController {

  private final TodoService todoService;

  // 할일 목록에서 일정(일) 불러오기
  @GetMapping("/todolist/daily")
  public ResponseEntity<List<TodoResponseDTO>> findDailyTodoList(@RequestParam("date")LocalDate date,
                                                                 @RequestParam("next")int next){
    TodoDailyRequestDTO todoDailyRequestDTO = new TodoDailyRequestDTO();
    todoDailyRequestDTO.setDate(date);
    todoDailyRequestDTO.setNext(next);
    return ResponseEntity.ok(todoService.getTodoListDaily(todoDailyRequestDTO));
  }

  // 메인 달력에서 일정 불러오기
  @GetMapping("/calendar/daily")
  public ResponseEntity<List<TodoResponseDTO>> findCalendarTodoList(@RequestParam("date")LocalDate date){
    TodoRequestDTO todoRequestDTO = new TodoRequestDTO();
    todoRequestDTO.setDate(date);
    return ResponseEntity.ok(todoService.getTodoCalendarDaily(todoRequestDTO));
  }

  // 메인 화면에서 당일 안한 일정들 불러오기
  @GetMapping("/main/daily")
  public ResponseEntity<List<TodoMainResponseDTO>> findMainInCompleted(){

    return ResponseEntity.ok(todoService.getTodoMainDaily());
  }

  // 월별 일정 완/미완 불러오기
  @GetMapping("/calendar")
  public ResponseEntity<List<TodoCalendarResponseDTO>> findCalendarIsCompleted(@RequestParam("date")LocalDate date){
    TodoRequestDTO todoRequestDTO = new TodoRequestDTO();
    todoRequestDTO.setDate(date);
    return ResponseEntity.ok(todoService.getTodoCalendarCompleted(todoRequestDTO));
  }

  // 투두 생성
  @PostMapping
  public ResponseEntity<TodoResponseDTO> createTodo(@RequestParam("content")String content,
                                                    @RequestParam("categoryId")int categoryId,
                                                    @RequestParam("todoDay")LocalDate date){
    TodoCreateRequestDTO todo = new TodoCreateRequestDTO();
    todo.setContent(content);
    todo.setCategoryId(categoryId);
    todo.setTodoDay(date);
    return ResponseEntity.ok(todoService.insertTodo(todo));
  }

  // 투두 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removeTodo(@PathVariable("id") int id){
    todoService.deleteTodo(id);
    return ResponseEntity.noContent().build();
  }

  // 투두 수정
  @PatchMapping()
  public ResponseEntity<TodoResponseDTO> modifyTodo(@RequestParam("id") int id, @RequestParam("content")String content){
    TodoModifyRequestDTO todoModifyRequestDTO = new TodoModifyRequestDTO();
    todoModifyRequestDTO.setId(id);
    todoModifyRequestDTO.setContent(content);
    return ResponseEntity.ok(todoService.updateTodo(todoModifyRequestDTO));
  }

  // 투두 완료
  @PostMapping("/complete/{id}")
  public ResponseEntity<TodoResponseDTO> completeTodo(@PathVariable("id") int id){
    return ResponseEntity.ok(todoService.completeTodo(id));
  }

  //오늘 (어제 포함) 일정 완료하기
  @PostMapping("/complete/complateAll")
  public ResponseEntity<Boolean> allCompleteTodo(@RequestParam("date")LocalDate date){
    TodoRequestDTO todoRequestDTO = new TodoRequestDTO();
    todoRequestDTO.setDate(date);
    return ResponseEntity.ok(todoService.AllcompleteTodo(todoRequestDTO));
  }


}
