package com.todoslave.feedme.DTO;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TodoResponseDTO {

  private int id;
  private int categoryId;
  private String categoryName;
  private String content;
  private LocalDate createdAt;
  private int isCompleted;

}
