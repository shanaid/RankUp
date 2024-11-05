package com.todoslave.feedme.DTO;

import lombok.Data;

import java.util.List;

@Data
public class FeedCommentDTO {
    private String email;
    private int feedComentId;
    private String nickname;
    private String comment;
    private String time;
}
