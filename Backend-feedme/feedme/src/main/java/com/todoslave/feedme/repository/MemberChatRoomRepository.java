package com.todoslave.feedme.repository;


import com.todoslave.feedme.domain.entity.communication.MemberChatRoom;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

public interface MemberChatRoomRepository extends MongoRepository<MemberChatRoom, String> {

  // 방 번호 얻어오기
  @Query("{ 'participantIds': ?0 }")
  MemberChatRoom findByParticipantIdsContainingAll(List<Integer> participantIds);

  // 방 여러개 얻어오기
  List<MemberChatRoom> findAllByParticipantIdsContainingOrderByReceiveTime(List<Integer> participantIds);

  // save 메서드는 이미 몽고디비에 존재하므로 재 정의 할 필요 X

}
