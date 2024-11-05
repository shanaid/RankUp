package com.todoslave.feedme.service;

import com.todoslave.feedme.DTO.DiaryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;

public interface DiaryService {
    Page<DiaryResponseDTO> getDiaryList(Pageable pageable);

}

