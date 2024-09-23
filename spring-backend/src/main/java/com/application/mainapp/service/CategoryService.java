package com.application.mainapp.service;


import com.application.mainapp.dto.category.CategoryDTO;
import com.application.mainapp.dto.category.CategoryResponseDTO;
import com.application.mainapp.model.Category;
import com.application.mainapp.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public CategoryResponseDTO saveCategory(CategoryDTO categoryDTO){
        return this.modelMapper.map(this.categoryRepository.save(this.modelMapper.map(categoryDTO,Category.class)),CategoryResponseDTO.class);
    }

    public List<CategoryResponseDTO> getAllCategories(){
        List<Category> categories = this.categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<CategoryResponseDTO> getCategoryById(Long id) {
        Optional<Category> categoryOptional = this.categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            CategoryResponseDTO categoryResponseDTO = modelMapper.map(category, CategoryResponseDTO.class);
            return Optional.of(categoryResponseDTO);
        } else {
            return Optional.empty();
        }
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryDTO updatedCategory) {
        Optional<Category> existingCategory = this.categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            category.setName(updatedCategory.getName());
            category.setDescription(updatedCategory.getDescription());
            category = this.categoryRepository.save(category);
            return this.modelMapper.map(category,CategoryResponseDTO.class);
        } else {
            throw new RuntimeException("Category not found");
        }
    }

    public void deleteCategory(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
