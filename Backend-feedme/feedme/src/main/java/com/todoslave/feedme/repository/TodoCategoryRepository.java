package com.todoslave.feedme.repository;

import com.todoslave.feedme.domain.entity.task.TodoCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoCategoryRepository extends JpaRepository<TodoCategory, Integer> {

  List<TodoCategory> findAllByMemberId(int memberId);

}
