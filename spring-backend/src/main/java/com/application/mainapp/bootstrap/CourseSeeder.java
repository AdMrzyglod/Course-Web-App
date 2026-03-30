package com.application.mainapp.bootstrap;

import com.application.mainapp.model.*;
import com.application.mainapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CourseSeeder {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final IndividualUserRepository individualUserRepository;
    private final CourseCodeRepository courseCodeRepository;

    public void seed() {
        if (courseRepository.count() > 0) {
            return;
        }

        IndividualUser creator1 = individualUserRepository.findByEmail("user1@test.com").orElseThrow();
        IndividualUser creator2 = individualUserRepository.findByEmail("user2@test.com").orElseThrow();
        IndividualUser creator3 = individualUserRepository.findByEmail("user3@test.com").orElseThrow();

        createCourse("Java od Podstaw do Eksperta", "Kompletna ścieżka programisty Java.",
                BigDecimal.valueOf(199.99), "Programowanie", creator1,
                "sample-data/images/logo_1.png", "sample-data/files/example1.pdf", "sample-data/files/example2.pdf");

        createCourse("Nowoczesny React.js", "Budowa szybkich aplikacji SPA.",
                BigDecimal.valueOf(149.99), "Frontend", creator1,
                "sample-data/images/javascript_logo.png", "sample-data/files/example3.pdf", "sample-data/files/example4.pdf");

        createCourse("Spring Boot i Mikrousługi", "Zaawansowany backend w chmurze.",
                BigDecimal.valueOf(299.99), "Backend", creator2,
                "sample-data/images/logo_2.png", "sample-data/files/example5.pdf", "sample-data/files/example6.pdf");

        createCourse("Python w Data Science", "Analiza danych i automatyzacja.",
                BigDecimal.valueOf(249.99), "Uczenie Maszynowe", creator2,
                "sample-data/images/logo_3.png", "sample-data/files/example7.pdf", "sample-data/files/example8.pdf");

        createCourse("HTML5 i CSS3 Masterclass", "Perfekcyjny design i responsywność.",
                BigDecimal.valueOf(99.99), "Frontend", creator2,
                "sample-data/images/html_logo.png", "sample-data/files/example1.pdf", "sample-data/files/example4.pdf");

        createCourse("Deep Learning z TensorFlow", "Sieci neuronowe w praktyce.",
                BigDecimal.valueOf(399.99), "Uczenie Maszynowe", creator3,
                "sample-data/images/logo_4.png", "sample-data/files/example2.pdf", "sample-data/files/example6.pdf");
    }

    private void createCourse(String name, String description, BigDecimal price, String categoryName, IndividualUser creator, String imagePath, String pdf1, String pdf2) {
        Category category = categoryRepository.findByName(categoryName);

        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setPrice(price);
        course.setCategory(category);
        course.setCreator(creator);

        try {
            ClassPathResource imgRes = new ClassPathResource(imagePath);
            if (imgRes.exists()) {
                ImageData img = new ImageData();
                img.setName(imgRes.getFilename());
                img.setType("image/png");
                img.setData(imgRes.getInputStream().readAllBytes());
                img.setCourse(course);
                course.setImage(img);
            }
        } catch (IOException ignored) {}

        Section s1 = createSection("Moduł 1: Fundamenty", "Podstawowe zagadnienia i teoria.", course, 1);
        createSub(s1, "Lekcja 1.1: Wstęp", 1, pdf1);
        createSub(s1, "Lekcja 1.2: Pierwsze kroki", 2, pdf2);

        Section s2 = createSection("Moduł 2: Warsztat Praktyczny", "Budowa realnego projektu.", course, 2);
        createSub(s2, "Lekcja 2.1: Implementacja", 1, pdf1);
        createSub(s2, "Lekcja 2.2: Testowanie i Deploy", 2, pdf2);

        course.setSections(List.of(s1, s2));
        Course saved = courseRepository.save(course);

        for (int i = 0; i < 5; i++) {
            CourseCode cc = new CourseCode();
            cc.setCourse(saved);
            cc.setAccessCode(UUID.randomUUID().toString());
            courseCodeRepository.save(cc);
        }
    }

    private Section createSection(String name, String desc, Course course, int number) {
        Section s = new Section();
        s.setName(name);
        s.setDescription(desc);
        s.setCourse(course);
        s.setNumber(number);
        s.setSubsections(new ArrayList<>());
        return s;
    }

    private void createSub(Section s, String name, int num, String pdfPath) {
        Subsection sub = new Subsection();
        sub.setName(name);
        sub.setNumber(num);
        sub.setSection(s);

        try {
            ClassPathResource res = new ClassPathResource(pdfPath);
            if (res.exists()) {
                FileData fd = new FileData();
                fd.setName(res.getFilename());
                fd.setType("application/pdf");
                fd.setData(res.getInputStream().readAllBytes());
                fd.setSubsection(sub);
                sub.setFile(fd);
            }
        } catch (IOException ignored) {}
        s.getSubsections().add(sub);
    }
}