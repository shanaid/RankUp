package com.todoslave.feedme.DTO;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TodoCalendarResponseDTO {

  private LocalDate date;
  private int inCompleted;
  private int completed;
  private int total;

}
