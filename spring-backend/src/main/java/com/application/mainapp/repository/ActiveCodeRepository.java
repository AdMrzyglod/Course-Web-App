package com.application.mainapp.repository;


import com.application.mainapp.model.ActiveCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiveCodeRepository extends JpaRepository<ActiveCode, Long> {

    @Query("SELECT ac FROM ActiveCode ac WHERE ac.individualUser.platformUserID = :platformUserId AND ac.courseCode.course.courseID = :courseId")
    Optional<ActiveCode> findActiveCodeByPlatformUserIdAndCourseId(@Param("platformUserId") Long platformUserId, @Param("courseId") Long courseId);
    @Query("SELECT CASE WHEN COUNT(ac) > 0 THEN true ELSE false END FROM ActiveCode ac WHERE ac.individualUser.platformUserID = :platformUserId AND ac.courseCode.course.courseID = :courseId")
    boolean existsActiveCodeByPlatformUserIdAndCourseId(@Param("platformUserId") Long platformUserId, @Param("courseId") Long courseId);
}
