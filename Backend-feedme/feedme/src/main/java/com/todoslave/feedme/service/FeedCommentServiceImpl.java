package com.todoslave.feedme.service;

import com.todoslave.feedme.DTO.FeedCommentRequestDTO;
import com.todoslave.feedme.DTO.FeedCommentResponseDTO;
import com.todoslave.feedme.domain.entity.Feed.Feed;
import com.todoslave.feedme.domain.entity.Feed.FeedComment;
import com.todoslave.feedme.login.util.SecurityUtil;
import com.todoslave.feedme.repository.FeedCommentRepository;
import com.todoslave.feedme.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class FeedCommentServiceImpl implements FeedCommentService {

    private final FeedRepository feedRepository;
    private final FeedCommentRepository commentRepository;
    private final FeedCommentRepository feedCommentRepository;

    //피드 댓글 작성
    @Override
    public FeedCommentResponseDTO insertFeedCommend(FeedCommentRequestDTO feedCommentRequestDTO, int feedid) {
        FeedComment feedComment = new FeedComment();
        Feed feed = feedRepository.findById(feedid).orElse(null);

        if (feed == null) {
            // Feed가 존재하지 않으면 예외를 던지거나 적절한 처리를 합니다.
            throw new IllegalArgumentException("Feed가 존재하지 않습니다.");
        }

        feedComment.setFeed(feed);
        feedComment.setMember(SecurityUtil.getCurrentMember());
        feedComment.setContent(feedCommentRequestDTO.getContent());

        commentRepository.save(feedComment);

        return convertCommentToDTO(feedComment);
    }


    //댓글 수정
    @Override
    public FeedCommentResponseDTO modifyComment(FeedCommentRequestDTO feedCommentRequestDTO, int feedcommantId) {
        FeedComment feedComment = feedCommentRepository.findById(feedcommantId).orElse(null);
        //못찾아 왓을 때
        if (feedComment == null) {
           return null;
        }
        //내가 쓴 글이 아닐 때
        if (SecurityUtil.getCurrentMember().getId() != feedComment.getMember().getId()) {
            return null;
        }

        feedComment.setContent(feedCommentRequestDTO.getContent());
        feedComment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(feedComment);
        return convertCommentToDTO(feedComment);
    }

    //댓글 삭제
    @Override
    public boolean deleteFeedComment(int feedId) {
        FeedComment feedComment = feedCommentRepository.findById(feedId).orElse(null);
        if (feedComment == null|| SecurityUtil.getCurrentMember().getId() != feedComment.getMember().getId()) {
            return false;
        }
        commentRepository.delete(feedComment);
        return true;
        }


    //변환하기 댓글 -> FeedCommentResponseDTO로
    private FeedCommentResponseDTO convertCommentToDTO(FeedComment feedComment) {
        FeedCommentResponseDTO dto = new FeedCommentResponseDTO();
        dto.setNickname(feedComment.getMember().getNickname()); // Member 엔티티에서 닉네임 가져오기
        dto.setContent(feedComment.getContent());
        dto.setCreatedAt(feedComment.getCreatedAt());
        return dto;
    }

}
