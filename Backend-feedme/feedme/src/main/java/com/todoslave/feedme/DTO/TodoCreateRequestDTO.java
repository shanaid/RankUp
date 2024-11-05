package com.todoslave.feedme.DTO;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
public class TodoCreateRequestDTO {

  private String content;
  private int categoryId;
  private LocalDate todoDay;
}
