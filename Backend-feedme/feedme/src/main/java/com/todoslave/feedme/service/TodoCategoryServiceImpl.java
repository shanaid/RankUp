package com.todoslave.feedme.service;

import com.todoslave.feedme.DTO.TodoCategoryRequestDTO;
import com.todoslave.feedme.DTO.TodoCategoryResponseDTO;
import com.todoslave.feedme.domain.entity.membership.Member;
import com.todoslave.feedme.domain.entity.task.TodoCategory;
import com.todoslave.feedme.login.util.SecurityUtil;
import com.todoslave.feedme.repository.MemberRepository;
import com.todoslave.feedme.repository.TodoCategoryRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoCategoryServiceImpl implements TodoCategoryService {

  @Autowired
  private TodoCategoryRepository todoCategoryRepository;
  @Autowired
  private MemberRepository memberRepository;


  // 카테고리들 가져오기
  @Override
  public List<TodoCategoryResponseDTO> getCategories() {

    int memberId = SecurityUtil.getCurrentUserId();
    List<TodoCategory> result = todoCategoryRepository.findAllByMemberId(memberId);

    List<TodoCategoryResponseDTO> categories = new ArrayList<>();

    for(TodoCategory todoCategory : result){

      TodoCategoryResponseDTO todoCategoryResponseDTO = new TodoCategoryResponseDTO();
      todoCategoryResponseDTO.setId(todoCategory.getId());
      todoCategoryResponseDTO.setName(todoCategory.getName());
      categories.add(todoCategoryResponseDTO);

    }

    return categories;
  }

  // 카테고리 수정
  @Override
  @Transactional
  public TodoCategoryResponseDTO updateCategory(TodoCategoryRequestDTO todoCategoryRequestDTO) {

    TodoCategory todoCategory = todoCategoryRepository.findById(todoCategoryRequestDTO.getId()).orElseThrow();
    todoCategory.setName(todoCategoryRequestDTO.getName());
    TodoCategoryResponseDTO result = new TodoCategoryResponseDTO();
    result.setName(todoCategory.getName());
    result.setId(todoCategory.getId());

    return result;
  }

  // 카테고리 삭제
  @Override
  @Transactional
  public void deleteCategory(int id) {

    todoCategoryRepository.deleteById(id);

  }

  // 카테고리 등록
  @Override
  public TodoCategoryResponseDTO insertCategory(String name) {

    if(name==null){
      System.out.println("null인데용?");
    }

    Member member = SecurityUtil.getCurrentMember();

    TodoCategory todoCategory = new TodoCategory();

    System.out.println(" - -- 출력되나?"+name);
    System.out.println("- - - 출력되ㅣ시나요>"+member);

    todoCategory.setName(name);
    todoCategory.setMember(member);
    todoCategory = todoCategoryRepository.save(todoCategory);

    TodoCategoryResponseDTO todoCategoryResponseDTO = new TodoCategoryResponseDTO();
    todoCategoryResponseDTO.setId(todoCategory.getId());
    todoCategoryResponseDTO.setName(todoCategory.getName());

    return todoCategoryResponseDTO;
  }

}
