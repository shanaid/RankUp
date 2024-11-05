package com.todoslave.feedme.controller;

import com.todoslave.feedme.DTO.*;
import com.todoslave.feedme.service.FeedCommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedComment")
@RequiredArgsConstructor
public class FeedCommentController {

    private final FeedCommentService feedCommentService;

    // 피드 생성
    @Operation(summary = "댓글 생성")
    @PostMapping("/{feed_id}")
    public ResponseEntity<?> registComment(@PathVariable("feed_id") int feedId, @RequestBody FeedCommentRequestDTO feedCommentRequestDTO) {
        return ResponseEntity.ok(feedCommentService.insertFeedCommend(feedCommentRequestDTO,feedId));
    }

    // 피드 수정
    @Operation(summary = "댓글 수정")
    @PatchMapping("/{feedComment_Id}")
    public ResponseEntity<?> modifyComment(@PathVariable("feedComment_Id") int feedcommantId, @RequestBody FeedCommentRequestDTO feedCommentRequestDTO) {
        FeedCommentResponseDTO dto = feedCommentService.modifyComment(feedCommentRequestDTO, feedcommantId);
        if (dto == null) {
            String msg = "내가 쓴 글이 아니거나, 삭제된 글 입니다.";
            return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(dto);
    }

    // 피드 삭제
    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{feedComment_Id}")
    public ResponseEntity<?> delectFeedComment(@PathVariable int feedComment_Id) {
        boolean succes = feedCommentService.deleteFeedComment(feedComment_Id);
        if (succes) {
            String msg = "삭제되었습니다";
            return new ResponseEntity<String>(msg, HttpStatus.OK);
        }
        String msg = "내가 쓴 글이 아니거나, 삭제된 글 입니다.";
        return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
    }

}
