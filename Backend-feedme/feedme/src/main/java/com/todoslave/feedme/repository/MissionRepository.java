package com.todoslave.feedme.repository;
import com.todoslave.feedme.domain.entity.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByWeatherCondition(String weatherCondition);
}