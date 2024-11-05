package com.todoslave.feedme.domain.entity.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todoslave.feedme.domain.entity.membership.Member;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

// 할 일
@Entity @Data
@Table(name = "todo")
public class Todo {

    // 할일 ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 회원 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    // 할일 카테고리 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todoCategory_id")
    private TodoCategory todoCategory;

    // 내용
    @Column(nullable = false, length = 1000)
    private String content;

    // 생성 일자
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    //완료 여부
    @Column(name = "is_completed",nullable = false)
    private int isCompleted;

  //==연관관계 메서드==//
  public void setMember(Member member) {
    this.member = member;
    member.getTodos().add(this);
  }

  public void setTodoCategory(TodoCategory todoCategory) {
    this.todoCategory = todoCategory;
    todoCategory.getTodos().add(this);
  }

}
