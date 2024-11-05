package com.todoslave.feedme.domain.entity.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todoslave.feedme.domain.entity.membership.Member;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "dayoff")
public class DayOff {

    //날자 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 회원 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    // 생성일자
    @Column(name = "end_day", nullable = false)
    private LocalDate endDay;

    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getDayOffs().add(this);
    }
}
