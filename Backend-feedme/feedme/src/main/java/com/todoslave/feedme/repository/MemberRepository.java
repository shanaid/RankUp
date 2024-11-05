package com.todoslave.feedme.repository;

import com.todoslave.feedme.domain.entity.membership.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findById(Integer id);
    List<Member> findByNicknameContaining(String searchValue);
    Optional<Member> findByNickname(String nickname);

    List<Member> findAllByBirthday(LocalDate date);
    boolean existsByNickname(String nickname);
  
}

