package com.todoslave.feedme.DTO;

import lombok.Data;

@Data
public class FriendResponseDTO {

  private int friendId;
  private String counterpartNickname;
//  private String creatureimg;
private byte[] creatureImg;

}
