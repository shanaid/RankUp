package com.todoslave.feedme.repository;

import com.todoslave.feedme.domain.entity.Feed.Feed;
import com.todoslave.feedme.domain.entity.Feed.FeedLike;
import com.todoslave.feedme.domain.entity.membership.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {
    boolean existsByMemberAndFeed(Member member, Feed feed);
    FeedLike findByMemberAndFeed(Member member, Feed feed);
    
}
