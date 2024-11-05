package com.todoslave.feedme.domain.entity.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todoslave.feedme.domain.entity.membership.Member;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;


@Entity
@Data
@Table(name = "creaturetodo")
public class CreatureTodo {

    //크리쳐 숙제 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 회원 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    // 내용
    @Column(name = "content", nullable = false, length = 255)
    private String content;


    // 생성일자
//    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    // 완료여부
    @Column(name = "is_completed")
    private int isCompleted = 0;

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getCreatureTodos().add(this);
    }

}
