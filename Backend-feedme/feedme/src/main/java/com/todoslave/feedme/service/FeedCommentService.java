package com.todoslave.feedme.service;

import com.todoslave.feedme.DTO.*;

public interface FeedCommentService {

    //댓글 등록
    FeedCommentResponseDTO insertFeedCommend(FeedCommentRequestDTO FeedCommentRequestDTO, int feedid);

    //댓글 수정
    FeedCommentResponseDTO modifyComment(FeedCommentRequestDTO feedCommentRequestDTO, int feedcommantId);

    //댓글 삭제
    boolean deleteFeedComment(int feedId);



}
