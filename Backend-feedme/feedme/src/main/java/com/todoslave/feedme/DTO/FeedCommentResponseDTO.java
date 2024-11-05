package com.todoslave.feedme.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedCommentResponseDTO {
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
}
