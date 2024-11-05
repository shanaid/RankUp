package com.todoslave.feedme.controller;

import com.todoslave.feedme.DTO.DiaryRequestDTO;
import com.todoslave.feedme.DTO.DiaryResponseDTO;
import com.todoslave.feedme.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/list")
    public ResponseEntity<Page<DiaryResponseDTO>> getDiaryList(
            @RequestBody DiaryRequestDTO paginationRequest) {

        int skip = paginationRequest.getSkip();
        int limit = paginationRequest.getLimit();

        Pageable pageable = PageRequest.of(skip / limit, limit, Sort.by("createdAt").descending());
        Page<DiaryResponseDTO> diaryPage = diaryService.getDiaryList(pageable);

        return ResponseEntity.ok(diaryPage);
    }

}