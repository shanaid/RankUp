package com.todoslave.feedme.service;

import com.todoslave.feedme.domain.entity.mission.Mission;

public interface MissionService {

    // 지정된 인덱스 범위 내에서 임의의 미션을 가져옵니다.
    Mission getRandomMissionByIndices(int startIndex, int endIndex);

    // 비가 오는 날의 미션을 가져옵니다.
    Mission getRainyMission();

    // 눈이 오는 날의 미션을 가져옵니다.
    Mission getSnowyMission();

    // 맑은 날의 미션을 가져옵니다.
    Mission getSunnyMission();

    // 흐린 날의 미션을 가져옵니다.
    Mission getCloudyMission();

    // 기본 미션을 가져옵니다.
    Mission getDefaultMission();
}
