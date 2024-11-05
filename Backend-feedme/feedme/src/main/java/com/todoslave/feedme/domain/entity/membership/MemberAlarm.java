package com.todoslave.feedme.domain.entity.membership;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Time;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "memberAlarm")
public class MemberAlarm {

  @Id
  @GeneratedValue
  private int id;

  // 회원 ID
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  @JsonBackReference
  private Member member;

  @Column(name = "alarm_time")
  private int alarmTime;

}
