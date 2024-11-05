package com.todoslave.feedme.mapper;

import com.todoslave.feedme.DTO.FriendReqResponseDTO;
import com.todoslave.feedme.domain.entity.avatar.Creature;
import com.todoslave.feedme.domain.entity.communication.FriendRequest;
import com.todoslave.feedme.repository.CreatureRepository;
import com.todoslave.feedme.util.FlaskClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestMapper {

  private final CreatureRepository creatureRepository;
  private final FlaskClientUtil flaskClientUtil;

  @Autowired
  public FriendRequestMapper(CreatureRepository creatureRepository, FlaskClientUtil flaskClientUtil) {
    this.creatureRepository = creatureRepository;
    this.flaskClientUtil = flaskClientUtil;
  }

  public FriendReqResponseDTO toDto(FriendRequest friendRequest) {
    FriendReqResponseDTO dto = new FriendReqResponseDTO();
    dto.setId(friendRequest.getId());
    dto.setCounterpartNickname(friendRequest.getCounterpartId().getNickname());
    Creature creature = creatureRepository.findByMemberId(friendRequest.getId());

//    dto.setCreatureImg("https://i11b104.p.ssafy.io/image/creature/"+creature.getMember().getId()+"_"+creature.getLevel());
    dto.setCreatureImg(flaskClientUtil.getCreatureImageAsByteArray(friendRequest.getMember().getNickname(), friendRequest.getMember().getCreature().getId(), friendRequest.getMember().getCreature().getLevel()));

    return dto;
  }

}
