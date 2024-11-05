package com.todoslave.feedme.DTO;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
public class FeedResponseDTO {

    private int id;
//    private String img;
    private byte[] img;
    private String content;
    private String author;
    private String likeCnt;

    private LocalDateTime createdAt;

}
