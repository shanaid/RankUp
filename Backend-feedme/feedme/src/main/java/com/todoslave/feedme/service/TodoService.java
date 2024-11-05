package com.todoslave.feedme.service;

import com.todoslave.feedme.DTO.TodoCalendarResponseDTO;
import com.todoslave.feedme.DTO.TodoCreateRequestDTO;
import com.todoslave.feedme.DTO.TodoDailyRequestDTO;
import com.todoslave.feedme.DTO.TodoResponseDTO;
import com.todoslave.feedme.DTO.TodoMainResponseDTO;
import com.todoslave.feedme.DTO.TodoModifyRequestDTO;
import com.todoslave.feedme.DTO.TodoRequestDTO;
import com.todoslave.feedme.domain.entity.task.Todo;

import java.time.LocalDate;
import java.util.List;

public interface TodoService {

  // 할일 목록에서 일정 불러오기
  public List<TodoResponseDTO> getTodoListDaily(TodoDailyRequestDTO todoDailyRequestDTO);

  // 메인 달력에서 일정 불러오기
  public List<TodoResponseDTO> getTodoCalendarDaily(TodoRequestDTO todoRequestDTO);

  // 메인화면에서 당일 안한 일정들 불러오기
  public List<TodoMainResponseDTO> getTodoMainDaily();

  // 월별 일정 완/미완 불러오기 (크리쳐 미션 포함)
  public List<TodoCalendarResponseDTO> getTodoCalendarCompleted(TodoRequestDTO todoRequestDTO);

  // 일정 추가하기
  public TodoResponseDTO insertTodo(TodoCreateRequestDTO todoCreateRequestDTO);

  //일정 삭제하기
  public void deleteTodo(int todoId);

  //일정 수정하기
  public TodoResponseDTO updateTodo(TodoModifyRequestDTO todoModifyRequestDTO);

  //일정 완료하기
  public TodoResponseDTO completeTodo(int todoId);

  //모든 일정 끝내기 (크리쳐 미션 포함)
  public boolean AllcompleteTodo(TodoRequestDTO todoRequestDTO);

  // 그림일기 생성

}
