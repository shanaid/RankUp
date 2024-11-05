package com.todoslave.feedme.DTO;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberChatMessageResponseDTO {

  private String sendNickname; // 보내는 사람 닉네임
  private String message; // 메세지 내용
  private LocalDateTime transmitAt; // 전송 시간

}
