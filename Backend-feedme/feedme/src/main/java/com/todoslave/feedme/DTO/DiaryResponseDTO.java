package com.todoslave.feedme.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DiaryResponseDTO {

    private String content;
    private LocalDate createdAt;
//    private String diaryImg;
//    private byte[] diaryimg;
}
