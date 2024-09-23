package com.application.mainapp.repository;


import com.application.mainapp.dto.coursecode.CourseCodeAccessCodeDTO;
import com.application.mainapp.model.CourseCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseCodeRepository extends JpaRepository<CourseCode, Long> {

    @Query("SELECT new com.application.mainapp.dto.coursecode.CourseCodeAccessCodeDTO(cc.courseCodeID,cc.accessCode) " +
            "FROM CourseCode cc WHERE cc.orderDetails IS NULL AND cc.course.courseID = :courseId")
    List<CourseCodeAccessCodeDTO> findCourseCodeAccessCodeDTOByOrderDetailsIsNullAndCourseId(@Param("courseId") Long courseId);

    @Query("SELECT cc FROM CourseCode cc WHERE cc.orderDetails IS NULL AND cc.course.courseID = :courseId")
    List<CourseCode> findCourseCodesByOrderDetailsIsNullAndCourseId(@Param("courseId") Long courseId);

    @Query("SELECT COUNT(cc) FROM CourseCode cc WHERE cc.orderDetails IS NULL AND cc.course.courseID = :courseId")
    int findNumberOfCourseCodesByOrderDetailsIsNullAndCourseId(@Param("courseId") Long courseId);

    @Query("SELECT cc FROM CourseCode cc WHERE cc.accessCode = :accessCode AND cc.course.courseID = :courseId")
    Optional<CourseCode> findCourseCodeByAccessCodeAndCourseId(@Param("accessCode") String accessCode, @Param("courseId") Long courseId);

}
