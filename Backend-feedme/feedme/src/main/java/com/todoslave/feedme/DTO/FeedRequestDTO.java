package com.todoslave.feedme.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FeedRequestDTO {

    private LocalDate diaryDate; //언제

    private String content;

}
