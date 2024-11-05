package com.todoslave.feedme.domain.entity.Feed;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todoslave.feedme.domain.entity.membership.Member;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Feed {

    // 피드 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 회원 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    //그림일기 날자
    @Column(name = "diary_day")
    private LocalDate diaryDay;

    //닉네임
    @Column(name = "nickname")
    private String nickname;

    // 피드 내용
    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    // 생성 시간
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정 날짜
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // 좋아요 수
    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    // 피드 댓글과 매핑
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    private List<FeedComment> feedComments = new ArrayList<>();

    // 좋아요와 매핑
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    private List<FeedLike> feedLikes = new ArrayList<>();

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getFeeds().add(this);
    }
}
