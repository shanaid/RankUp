package com.todoslave.feedme.domain.entity.communication;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "memberchatmessage")
public class MemberChatMessage {

    @Id
    private String id;
    @Field("memberChatRoom_id")
    private String memberChatRoomId;
    @Field("send_id")
    private int sendId;
    private String content;

    @CreatedDate
    @Field("transmit_at")
    private LocalDateTime transmitAt;

    @Override
    public String toString() {
        return "MemberChatMessage{" +
            "id='" + id + '\'' +
            ", memberChatRoomId='" + memberChatRoomId + '\'' +
            ", sendId='" + sendId + '\'' +
            ", content='" + content + '\'' +
            ", transmitAt=" + transmitAt +
            '}';
    }

}