package com.todoslave.feedme.domain.entity.Feed;//package com.todoslave.feedme.domain.entity.Feed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todoslave.feedme.domain.entity.membership.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class FeedLike {

    @EmbeddedId
    private LikeId id = new LikeId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    @JsonIgnore  // 순환 참조 방지를 위해 JSON 변환 시 이 필드를 무시합니다.
    @ToString.Exclude // Lombok의 toString()에서 이 필드를 제외합니다.
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("feedId")
    @JoinColumn(name = "feed_id")
    @JsonIgnore  // 순환 참조 방지를 위해 JSON 변환 시 이 필드를 무시합니다.
    @ToString.Exclude // Lombok의 toString()에서 이 필드를 제외합니다.
    private Feed feed;

    @Data
    @EqualsAndHashCode
    @Embeddable
    public static class LikeId implements Serializable {
        private int memberId;
        private int feedId;

        public LikeId() {}

        public LikeId(int memberId, int feedId) {
            this.memberId = memberId;
            this.feedId = feedId;
        }
    }
}
