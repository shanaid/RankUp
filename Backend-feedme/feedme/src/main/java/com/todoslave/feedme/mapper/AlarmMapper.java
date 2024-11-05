package com.todoslave.feedme.mapper;

import com.todoslave.feedme.DTO.AlarmResponseDTO;
import com.todoslave.feedme.domain.entity.alarm.Alarm;

public class AlarmMapper {

  public static AlarmResponseDTO toDto(Alarm alarm){
    AlarmResponseDTO dto = new AlarmResponseDTO();
    dto.setAlarmId(alarm.getId());
    dto.setContent(alarm.getContent());
    return dto;
  }

}
