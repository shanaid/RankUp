package com.todoslave.feedme.service;


import com.todoslave.feedme.DTO.CreatureInfoResponseDTO;
import com.todoslave.feedme.domain.entity.avatar.Creature;

import com.todoslave.feedme.domain.entity.membership.Member;
import org.springframework.web.multipart.MultipartFile;


public interface CreatureService {


    //크리쳐 만들기
    Creature createFristCreature(String keyword, MultipartFile photo, String creatureName);
    //크리쳐 정보 가져오기
    CreatureInfoResponseDTO creatureInfo(Member member);
    //크리쳐 삭제하기
    boolean removeCreature();
    //크리쳐 경험치 올리기
    void expUp(int toDoCnt);

    boolean checkNickname(String creatureName);
}
