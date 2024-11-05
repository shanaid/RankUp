package com.todoslave.feedme.DTO;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MemberChatListResponseDTO {

  // 방 번호
  private String friendId;
  // 상대방 닉네임
  private String counterpartNickname;
  // 상대방 크리쳐 이미지
  private byte[] avatar;
  // 채팅 받은 시간
  private LocalDateTime receiveTime;
  // 최근에 채팅방 읽었는 지 확인
  private int isChecked;

}
