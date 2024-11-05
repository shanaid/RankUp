package com.todoslave.feedme.repository;

import com.todoslave.feedme.domain.entity.Feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Integer> {

    @Query("SELECT f FROM Feed f WHERE f.createdAt >= :startDate ORDER BY f.createdAt DESC")
    List<Feed> findRecentFeeds(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT f FROM Feed f WHERE f.createdAt >= :startDate AND (f.member.id = :memberId OR f.member.id IN (SELECT fr.counterpart.id FROM Friend fr WHERE fr.member.id = :memberId)) ORDER BY f.createdAt DESC")
    List<Feed> findRecentFeedsByFriendsAndMe(@Param("startDate") LocalDateTime startDate, @Param("memberId") int memberId);

}
