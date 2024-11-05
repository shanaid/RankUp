package com.todoslave.feedme.controller;

import com.todoslave.feedme.DTO.*;
import com.todoslave.feedme.service.CreatureTodoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/creatureTodo")
@RequiredArgsConstructor
public class CreatureTodoController {

    @Autowired
    final private CreatureTodoService creatureTodoService;

    // 크리쳐 투두 생성
    @Operation(summary = "크리쳐 투두 생성 / 있으면 생성 X")
    @GetMapping("/{weather}")
    public ResponseEntity<List<CreatureTodoResponseDTO>> createTodo(@PathVariable("weather") String weather) {
        List<CreatureTodoResponseDTO> list = new ArrayList<>();

        if(weather==null){
            list = creatureTodoService.insertTodo("default");
        }else {
            list = creatureTodoService.insertTodo(weather);
        }

        return ResponseEntity.ok(list);
    }

    // 투두 완료
    @PostMapping("/complete/{id}")
    @Operation(summary = "투두 일정 하나 완료/취소")
    public ResponseEntity<CreatureTodoResponseDTO> completeTodo(@PathVariable("id") int id){
        return ResponseEntity.ok(creatureTodoService.completeTodo(id));
    }

    // 오늘 완료되지 않은 투두 목록 가져오기
    @GetMapping("/main/daily")
    @Operation(summary = "오늘 완료되지 않은 크리쳐 투두 목록 가져오기")
    public ResponseEntity<List<CreatureTodoResponseDTO>> getCreatureTodoMainDaily() {
        List<CreatureTodoResponseDTO> list = creatureTodoService.getCreatureTodoMainDaily();
        return ResponseEntity.ok(list);
    }

    //  메인 달력에서 일정 불러오기
    @GetMapping("/calendar/daily")
    @Operation(summary = "해당일의 크리쳐 투두 목록 가져오기")
    public ResponseEntity<List<CreatureTodoResponseDTO>> findCalendarCreatureTodoList(@RequestParam("date") LocalDate date) {
        CretureTodoRequestDTO cretureTodoRequestDTO = new CretureTodoRequestDTO();
        cretureTodoRequestDTO.setDate(date);
        List<CreatureTodoResponseDTO> list = creatureTodoService.getCreatureTodoCalendarDaily(cretureTodoRequestDTO);
        return ResponseEntity.ok(list);
    }

    // 할일 목록에서 일정(일) 불러오기
    @Operation(summary = "할일 목록에서 일정(일) 불러오기 - 다음날 이동 가능하게")
    @GetMapping("/todolist/daily")
    public ResponseEntity<List<CreatureTodoResponseDTO>> findDailyCreatureTodoList(@RequestParam("date") LocalDate date){
        CreatureTodoDailyRequestDTO creatureTodoDailyRequestDTO = new CreatureTodoDailyRequestDTO();
        creatureTodoDailyRequestDTO.setDate(date);

        return ResponseEntity.ok(creatureTodoService.getCreatureTodoListDaily(creatureTodoDailyRequestDTO));
    }



}