package com.todoslave.feedme.service;

import com.todoslave.feedme.domain.entity.task.DayOff;
import com.todoslave.feedme.login.util.SecurityUtil;
import com.todoslave.feedme.repository.CreatureTodoReposito;
import com.todoslave.feedme.repository.DayOffRepository;
import com.todoslave.feedme.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;


@Service
public class DayOffServiceImpl implements DayOffService {

    private final DayOffRepository dayOffRepository;

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private CreatureTodoReposito creatureTodoReposito;

    @Autowired
    public DayOffServiceImpl(DayOffRepository dayOffRepository) {
        this.dayOffRepository = dayOffRepository;
    }

    @Override
    public Optional<DayOff> findDayOffByMemberIdAndDate(int memberId, LocalDate date) {
        return dayOffRepository.findByMemberIdAndEndDay(memberId, date);
    }

    @Override
    public DayOff saveDayOff(DayOff dayOff) {
        return dayOffRepository.save(dayOff);
    }

    @Override
    public boolean isActionAllowed(int memberId, LocalDate date) {
        return dayOffRepository.countByMemberIdAndEndDay(memberId, date) == 0;
    }

    @Override
    public boolean notFindClearData(LocalDate date) {
        long inCompleted = todoRepository.countTodoByDateAndIsCompleted(date, 1)+creatureTodoReposito.countByCreatedAtAndIsCompleted(date,1);
        if(inCompleted == 0){ //그날에 한게 없다?
            return false;
        }
        return true; //그날에 한것이 있다!
    }

    @Override
    public void deleteDayOff(int id) {
        dayOffRepository.deleteById(id);
    }



//    //false면 있는거 이게 한 적이 있나 없나
//    boolean checked = isActionAllowed(SecurityUtil.getCurrentMember().getId(), creatureTodo.getCreatedAt());
//
//
//    //아 생각해보니까 이러면 없는날에 안되네,,,
//    long inCompleted = todoRepository.countTodoByDateAndIsCompleted(creatureTodo.getCreatedAt(), 0)+creatureTodoReposito.countByCreatedAtAndIsCompleted(creatureTodo.getCreatedAt(),0);
//        if(inCompleted==0){
//        checked=false;
//    }



}


//package com.todoslave.feedme.service;
//
//
//import com.todoslave.feedme.domain.entity.task.DayOff;
//import com.todoslave.feedme.repository.DayOffRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class DayOffService {
//
//    private final DayOffRepository dayOffRepository;
//
//
//    @Autowired
//    public DayOffService(DayOffRepository dayOffRepository) {
//        this.dayOffRepository = dayOffRepository;
//    }
//    //맴버 아이디랑 날자로 찾기
//    public Optional<DayOff> findDayOffByMemberIdAndDate(int memberId, LocalDate date) {
//        return dayOffRepository.findByMemberIdAndEndDay(memberId, date);
//    }
//    //데이오프 만들기
//    public DayOff saveDayOff(DayOff dayOff) {
//        return dayOffRepository.save(dayOff);
//    }
//
//    //있는지 검사
//    public boolean isActionAllowed(int memberId, LocalDate date) {
//        // 특정 날짜에 한 개라도 DayOff가 있으면 false 반환
//        return dayOffRepository.countByMemberIdAndEndDay(memberId, date) == 0;
//    }
//    //쓸일 없어
//    public void deleteDayOff(int id) {
//        dayOffRepository.deleteById(id);
//    }
//}