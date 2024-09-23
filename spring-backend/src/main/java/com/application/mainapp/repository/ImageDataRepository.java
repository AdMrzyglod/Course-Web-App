package com.application.mainapp.repository;


import com.application.mainapp.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {

    @Query("SELECT image FROM ImageData image WHERE image.course.courseID = :courseID")
    Optional<ImageData> findImageDataByCourse_CourseID(@Param("courseID") Long courseID);
}
