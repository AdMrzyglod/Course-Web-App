package com.application.mainapp.bootstrap;

import com.application.mainapp.model.Category;
import com.application.mainapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategorySeeder {

    private final CategoryRepository categoryRepository;

    public void seed() {
        if (categoryRepository.count() > 0) {
            return;
        }

        Category cat1 = new Category();
        cat1.setName("Programowanie");
        cat1.setDescription("Opis - Programowanie");

        Category cat2 = new Category();
        cat2.setName("Frontend");
        cat2.setDescription("Opis - Frontend");

        Category cat3 = new Category();
        cat3.setName("Backend");
        cat3.setDescription("Opis - Backend");

        Category cat4 = new Category();
        cat4.setName("Uczenie Maszynowe");
        cat4.setDescription("Opis - Uczenie Maszynowe");

        categoryRepository.saveAll(List.of(cat1, cat2, cat3, cat4));
    }
}