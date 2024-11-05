package com.todoslave.feedme.service;


import com.todoslave.feedme.DTO.DiaryResponseDTO;
import com.todoslave.feedme.domain.entity.diary.PictureDiary;
import com.todoslave.feedme.login.util.SecurityUtil;
import com.todoslave.feedme.repository.DiaryRepository;
import com.todoslave.feedme.util.FlaskClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final FlaskClientUtil flaskClientUtil;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, FlaskClientUtil flaskClientUtil) {
        this.diaryRepository = diaryRepository;
        this.flaskClientUtil = flaskClientUtil;
    }

    @Override
    public Page<DiaryResponseDTO> getDiaryList(Pageable pageable) {
        Integer memberId = SecurityUtil.getCurrentMember().getId(); // 현재 사용자의 ID를 가져오는 방법
        return diaryRepository.findByMemberId(memberId, pageable)
                .map(this::convertToDTO);
    }


    private DiaryResponseDTO convertToDTO(PictureDiary diary) {
        DiaryResponseDTO dto = new DiaryResponseDTO();
        dto.setContent(diary.getContent());
        dto.setCreatedAt(diary.getCreatedAt());

//        dto.setDiaryimg(flaskClientUtil.getCreatureDiaryAsByteArray(diary.getMember().getNickname(),diary.getCreatedAt()));

        return dto;
    }
}
