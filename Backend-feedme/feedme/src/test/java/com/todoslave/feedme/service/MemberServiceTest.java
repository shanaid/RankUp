//package com.todoslave.feedme.service;
//
//
//import com.todoslave.feedme.domain.entity.membership.Emotion;
//import com.todoslave.feedme.domain.entity.membership.Member;
//import com.todoslave.feedme.repository.MemberRepository;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Date;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class MemberServiceTest {
//
//  @Autowired MemberService memberService;
//  @Autowired MemberRepository memberRepository;
//
//  @Test
//  @Rollback(false)
//  public void 회원가입() throws Exception {
//    //Given
//    Member member = new Member();
//
//    member.setEmail("asdf@gmail.com");
//    member.setToken("ABC");
//    member.setExp(0);
//    member.setStatus(Emotion.SAD);
//    member.setNickname("테스트1");
//    member.setLatitude(12.4);
//    member.setLongitude(12.4);
//
//    //When
//    int saveId = memberService.Join(member);
//
//    //Then
//    assertEquals(member, memberRepository.findById(saveId));
//  }
//
//  @Test
//  public void 중복_회원_예외() throws Exception {
//    //Given
//    Member member = new Member();
//    member.setEmail("asdf@gmail.com");
//    member.setToken("ABC");
//    member.setExp(0);
//    member.setStatus(Emotion.SAD);
//    member.setNickname("테스트1");
//    member.setLatitude(12.4);
//    member.setLongitude(12.4);
//
//    Member member2 = new Member();
//    member2.setEmail("asdf@gmail.com");
//    member2.setToken("ABC");
//    member2.setExp(0);
//    member2.setStatus(Emotion.SAD);
//    member2.setNickname("테스트2");
//    member2.setLatitude(12.4);
//    member2.setLongitude(12.4);
//
//    //When
//    memberService.Join(member2);
//    memberService.Join(member);
//
//    //Then
//
//    fail("예외가 발생해야한다.");
//
//  }
//
//}