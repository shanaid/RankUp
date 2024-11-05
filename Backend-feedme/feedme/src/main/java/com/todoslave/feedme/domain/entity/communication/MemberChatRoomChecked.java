package com.todoslave.feedme.domain.entity.communication;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "memberchatroomchecked")
public class MemberChatRoomChecked {

  @Id
  private String id;

  @Field("memberChatRoom_id")
  private String memberChatRoomId;

  @Field("member_id")
  private int memberId;

  @Field("is_checked")
  private int isChecked;

}
