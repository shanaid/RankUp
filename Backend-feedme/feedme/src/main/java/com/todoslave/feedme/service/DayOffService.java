package com.todoslave.feedme.service;

import com.todoslave.feedme.domain.entity.task.DayOff;

import java.time.LocalDate;
import java.util.Optional;

public interface DayOffService {

    // 맴버 아이디와 날짜로 DayOff 찾기
    Optional<DayOff> findDayOffByMemberIdAndDate(int memberId, LocalDate date);

    // DayOff 생성
    DayOff saveDayOff(DayOff dayOff);

    // 특정 날짜에 DayOff가 있는지 확인 -> 이거로 확인하고
    boolean isActionAllowed(int memberId, LocalDate date);

    // 이게 이제 그날의 끝낸게 있나 없나 확인
    boolean notFindClearData(LocalDate date);

    // DayOff 삭제
    void deleteDayOff(int id);




}
