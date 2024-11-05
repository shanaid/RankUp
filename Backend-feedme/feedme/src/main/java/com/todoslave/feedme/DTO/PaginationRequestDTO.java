package com.todoslave.feedme.DTO;

import lombok.Data;

@Data
public class PaginationRequestDTO {

  private int skip;
  private int limit;
  private int memberId;

}
