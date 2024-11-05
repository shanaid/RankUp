package com.todoslave.feedme.DTO;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;

@Data
public class TodoDailyRequestDTO {

  private LocalDate date;
  private int next;

}
