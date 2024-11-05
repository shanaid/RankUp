//package com.todoslave.feedme.domain.entity.membership;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Data
//@Table(name = "memberspace")
//public class MemberSpace {
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
//    //위도
//    @Column(name = "latitude")
//    private Double latitude;
//
//    //경도
//    @Column(name = "longitude")
//    private Double longitude;
//}
