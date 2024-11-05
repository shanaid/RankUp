package com.todoslave.feedme.controller;

import com.todoslave.feedme.login.util.SecurityUtil;
import com.todoslave.feedme.service.DayOffService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/dayoff")
@RequiredArgsConstructor
public class DayOffController {

    private final DayOffService dayOffService;

    //여기서 기존에 일기 쓴 기록이 있거나, 아니면 해당일에 한게 아무것도 없으면 버튼이 안눌리게 만들어 줘야해
    @Operation(summary = "일기 생성 버튼 활성화")
    @GetMapping("/{canFinishDay}")
    public ResponseEntity<Boolean> checkDiaryCreationEligibility(@PathVariable LocalDate canFinishDay) {

        int userId = SecurityUtil.getCurrentUserId();

        // 해당 날짜에 이미 완료된 작업이 있는지 확인
        boolean actionAllowed = dayOffService.isActionAllowed(userId, canFinishDay);

        // 해당 날짜에 기록이 없는지 확인 (기록이 없으면 true)
        boolean hasNoClearData = dayOffService.notFindClearData(canFinishDay);

        System.out.println("테스팅");
        System.out.println(actionAllowed); // 있어서 false
        System.out.println(hasNoClearData); // 없어서 true
        
        // 버튼이 비활성화 되어야 하는 경우 (이미 완료된 작업이 있거나 기록이 없는 경우)
        if (!actionAllowed || !hasNoClearData) {
            return ResponseEntity.ok(false);
        }

        // 버튼이 활성화 되어야 하는 경우
        return ResponseEntity.ok(true);
    }



}
