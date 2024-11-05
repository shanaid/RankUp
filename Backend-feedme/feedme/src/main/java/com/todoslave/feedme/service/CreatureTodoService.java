package com.todoslave.feedme.service;

import com.todoslave.feedme.DTO.*;

import java.time.LocalDate;
import java.util.List;

public interface CreatureTodoService {

    // 크리쳐 일정 생성 (날씨 영향)
    List<CreatureTodoResponseDTO> insertTodo(String weather);

    // 해당 날의 일정 가져오기
    List<CreatureTodoResponseDTO> getCreatureTodoCalendarDaily(CretureTodoRequestDTO cretureTodoRequestDTO);

    // 내일날 어제날 가져올 수 있게!
    List<CreatureTodoResponseDTO> getCreatureTodoListDaily(CreatureTodoDailyRequestDTO creatureTodoDailyRequestDTO);

    // 일정 하나 완료/취소 하기
    CreatureTodoResponseDTO completeTodo(int CreaturetoId);

    // 메인화면에서 당일 안한 일정들 불러오기 (할일 목록에서 일정 불러오기)
    List<CreatureTodoResponseDTO> getCreatureTodoMainDaily();




}