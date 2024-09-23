package com.application.mainapp.controller;


import com.application.mainapp.dto.category.CategoryDTO;
import com.application.mainapp.dto.category.CategoryResponseDTO;
import com.application.mainapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins="*")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        return new ResponseEntity<>(this.categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        Optional<CategoryResponseDTO> categoryOptional = this.categoryService.getCategoryById(id);
        return categoryOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CategoryResponseDTO> saveCategory(@RequestBody CategoryDTO categoryDTO){
        CategoryResponseDTO newCategory = this.categoryService.saveCategory(categoryDTO);
        return new ResponseEntity<>(newCategory, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable long id, @RequestBody CategoryDTO categoryDTO){
        CategoryResponseDTO updatedCategory = this.categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category deleted", HttpStatus.OK);
    }
}

