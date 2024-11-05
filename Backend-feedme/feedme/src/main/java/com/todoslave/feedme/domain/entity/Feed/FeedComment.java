package com.todoslave.feedme.domain.entity.Feed;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todoslave.feedme.domain.entity.membership.Member;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class FeedComment {

    //피드 댓글 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //피드 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    // 회원 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    //작성 댓글 내용
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 생성 시간
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 대댓글과 매핑
    @OneToMany(mappedBy = "feedComment", cascade = CascadeType.ALL)
    private List<FeedRecomment> feedRecomments = new ArrayList<>();


    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getFeedComments().add(this);
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
        feed.getFeedComments().add(this);
    }

}
