package com.todoslave.feedme.controller;

import com.todoslave.feedme.DTO.FeedDTO;
import com.todoslave.feedme.DTO.FeedModifyRequest;
import com.todoslave.feedme.DTO.FeedRequestDTO;
import com.todoslave.feedme.DTO.FeedResponseDTO;
import com.todoslave.feedme.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    @Autowired
    private final FeedService feedService;

    // 피드 생성
    @Operation(summary = "피드 생성")
    @PostMapping
    public ResponseEntity<FeedResponseDTO> createFeed(@RequestBody FeedRequestDTO feedRequestDTO) {
        FeedResponseDTO dto = feedService.insertFeed(feedRequestDTO);
        return ResponseEntity.ok(dto);
    }

    // 피드 수정
    @Operation(summary = "피드 수정")
    @PatchMapping("/{feedId}")
    public ResponseEntity<FeedResponseDTO> modifyFeed(@PathVariable int feedId, @RequestBody FeedModifyRequest feedModifyRequest) {
        FeedResponseDTO dto = feedService.modifyFeed(feedId ,feedModifyRequest);
        return ResponseEntity.ok(dto);
    }

    // 피드 삭제
    @Operation(summary = "피드 삭제")
    @DeleteMapping("/{feedId}")
    public ResponseEntity<?> delectFeed(@PathVariable int feedId) {
        if(feedService.delectFeed(feedId)){
            return new ResponseEntity<String>("삭제되었습니다.", HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("본인글만 지우세요", HttpStatus.BAD_REQUEST);
        }
    }

    // 좋아요/좋아요 취소 버튼
    @Operation(summary = "피드 좋아요/취소")
    @PostMapping("/{feedId}/like")
    public void likeButton(@PathVariable int feedId) {
        feedService.toggleLike(feedId);
    }

    // 피드 보기
    @Operation(summary = "피드 보기(테스트)")
    @GetMapping
    public List<FeedDTO> getRecentFeeds() {
        return feedService.getRecentFeeds();
    }

    // 친구 피드 보기
    @Operation(summary = " 1달치 친구 피드 보기")
    @GetMapping("/recent/friends")
    public List<FeedDTO> getRecentFeedsByFriends() {
        return feedService.getRecentFeedsByFriends();
    }

}
