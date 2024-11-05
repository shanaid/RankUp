//package com.todoslave.feedme.domain.entity.membership;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//@Table(name = "memberrenewinfo")
//public class MemberRenewInfo {
//
//    // 회원 ID
//    @Id
//    @Column(name = "member_id")
//    private int memberId;
//
//    // 회원 ID를 PK, FK 동시 적용
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    //가입일
//    @CreationTimestamp
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime joinDate;
//
//    //닉네임
//    @Column(nullable = false)
//    private String nickname;
//
//}
