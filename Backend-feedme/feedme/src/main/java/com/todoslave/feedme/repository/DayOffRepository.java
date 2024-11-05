package com.todoslave.feedme.repository;

import com.todoslave.feedme.domain.entity.task.DayOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DayOffRepository extends JpaRepository<DayOff, Integer> {

    // 특정 회원의 특정 날짜의 DayOff를 찾습니다.
    Optional<DayOff> findByMemberIdAndEndDay(int memberId, LocalDate endDay);
    long countByMemberIdAndEndDay(int memberId, LocalDate date);
}