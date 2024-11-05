package com.todoslave.feedme.service;

import com.todoslave.feedme.DTO.FeedDTO;
import com.todoslave.feedme.DTO.FeedModifyRequest;
import com.todoslave.feedme.DTO.FeedRequestDTO;
import com.todoslave.feedme.DTO.FeedResponseDTO;

import java.util.List;

public interface FeedService {

    FeedResponseDTO insertFeed(FeedRequestDTO feedRequestDTO);

    FeedResponseDTO modifyFeed(int feedId, FeedModifyRequest feedModifyRequest);

    boolean delectFeed(int feedId);

    void toggleLike(int feedId);

    List<FeedDTO> getRecentFeeds();

    List<FeedDTO> getRecentFeedsByFriends();
}
