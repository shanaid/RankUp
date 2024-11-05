package com.todoslave.feedme.domain.entity.diary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todoslave.feedme.domain.entity.membership.Member;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Data @Table(name = "picturediary")
public class PictureDiary {

    // 그림일기 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 회원 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    //일기쓴 날
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    //일기 내용
    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getPictureDiary().add(this);
    }

}
