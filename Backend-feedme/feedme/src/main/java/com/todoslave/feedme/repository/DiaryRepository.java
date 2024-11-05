package com.todoslave.feedme.repository;

import com.todoslave.feedme.domain.entity.diary.PictureDiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<PictureDiary, Integer> {
    // Example: Find all PictureDiaries by Member ID
    List<PictureDiary> findByMemberId(Integer memberId);

    // Example: Find all PictureDiaries created on a specific date
    List<PictureDiary> findByCreatedAt(LocalDate createdAt);

    Page<PictureDiary> findByMemberId(Integer memberId, Pageable pageable);
}