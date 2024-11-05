package com.todoslave.feedme.DTO;

import lombok.Data;
import lombok.Getter;

@Data
public class TodoModifyRequestDTO {

  private int id;
  private String content;

}
