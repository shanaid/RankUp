package com.todoslave.feedme.DTO;

import lombok.Data;

@Data
public class TodoMainResponseDTO {

  private int id;
  private String content;
  private int isCompleted;

}
