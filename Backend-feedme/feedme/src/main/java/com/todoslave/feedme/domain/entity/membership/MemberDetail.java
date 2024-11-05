//package com.todoslave.feedme.domain.entity.membership;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.Getter;
//
//@Entity @Data
//@Table(name = "memberdetail")
//public class MemberDetail {
//
//    // 회원 ID
//    @Id
//    @Column(name = "member_id")
//    private int memberId;
//
//    // 회원 ID를 PK/FK 동시 적용
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    // 경험치
//    @Column(name = "exp", nullable = false, updatable = false)
//    private int exp;
//
//    // 감정
//    @Column(name = "status", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Emotion status; // BASIC, JOY, SAD
//
//}
