package com.todoslave.feedme.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreatureChatResponseDTO {
    private LocalDate day;
    private List<String> contents;
}
