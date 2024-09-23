package com.application.mainapp.repository;


import com.application.mainapp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT co FROM Course co " +
            "JOIN CourseCode cc ON co.courseID = cc.course.courseID " +
            "JOIN ActiveCode ac ON ac.activeCodeID = cc.activeCode.activeCodeID " +
            "WHERE ac.individualUser.platformUserID = :userId " +
            "ORDER BY co.courseID")
    List<Course> findCoursesByPlatformUserID(@Param(("userId")) long userId);


    @Query("SELECT co FROM Course co " +
            "WHERE co.creator.platformUserID = :userId " +
            "ORDER BY co.courseID")
    List<Course> findCreateCoursesByPlatformUserID(@Param(("userId")) long userId);
}
