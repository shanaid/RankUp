package com.todoslave.feedme.repository;

import com.todoslave.feedme.domain.entity.communication.MemberChatRoomChecked;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberChatRoomCheckedRepository
    extends MongoRepository<MemberChatRoomChecked, String> {

  MemberChatRoomChecked findByMemberChatRoomIdAndMemberId(String memberChatRoomId, int memberId);

}
