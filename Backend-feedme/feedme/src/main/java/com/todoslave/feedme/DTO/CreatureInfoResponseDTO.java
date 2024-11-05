package com.todoslave.feedme.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CreatureInfoResponseDTO {

    private String name;
//    private String img;
    private byte[] img;
    private int level;
    private int exp;
    private int day;

}
