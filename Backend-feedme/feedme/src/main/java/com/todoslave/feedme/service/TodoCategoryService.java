package com.todoslave.feedme.service;

import com.todoslave.feedme.DTO.TodoCategoryRequestDTO;
import com.todoslave.feedme.DTO.TodoCategoryResponseDTO;
import java.util.List;

public interface TodoCategoryService {

  public List<TodoCategoryResponseDTO> getCategories();

  public TodoCategoryResponseDTO updateCategory(TodoCategoryRequestDTO todoCategoryRequestDTO);

  public void deleteCategory(int id);

  public TodoCategoryResponseDTO insertCategory(String name);

}
