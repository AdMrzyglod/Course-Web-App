package com.application.mainapp.repository;


import com.application.mainapp.dto.platformorder.PlatformOrderCourseCodeInfoResponseDTO;
import com.application.mainapp.dto.platformorder.PlatformOrderCourseResponseDTO;
import com.application.mainapp.dto.platformorder.PlatformOrderListResponseDTO;
import com.application.mainapp.dto.platformorder.PlatformUserOrderListResponseDTO;
import com.application.mainapp.model.PlatformOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PlatformOrderRepository extends JpaRepository<PlatformOrder, Long> {

    @Query("SELECT SUM(od.coursePrice) FROM OrderDetails od WHERE od.platformOrder.platformOrderID = :orderId")
    BigDecimal platformOrderTotalPrice(@Param("orderId") long orderId);

    @Query("SELECT po FROM PlatformOrder po WHERE po.individualUser.platformUserID = :userId")
    List<PlatformOrder> findPlatformOrderByPlatformUserId(@Param(("userId")) long userId);

    @Query("SELECT new com.application.mainapp.dto.platformorder.PlatformOrderListResponseDTO(po.platformOrderID, po.createTimestamp, COUNT(od), SUM(od.coursePrice)) " +
            "FROM PlatformOrder po JOIN OrderDetails od ON po.platformOrderID=od.platformOrder.platformOrderID WHERE po.individualUser.platformUserID=:userId " +
            "GROUP BY po.platformOrderID")
    List<PlatformOrderListResponseDTO> findPlatformOrderListResponseDTO(@Param(("userId")) long userId);

    @Query("SELECT new com.application.mainapp.dto.platformorder.PlatformUserOrderListResponseDTO(po.platformOrderID, po.createTimestamp, COUNT(od), SUM(od.coursePrice), po.individualUser.email) " +
            "FROM PlatformOrder po JOIN OrderDetails od ON po.platformOrderID=od.platformOrder.platformOrderID GROUP BY po.platformOrderID, po.individualUser.email")
    List<PlatformUserOrderListResponseDTO> findAllPlatformUserOrderListResponseDTO();

    @Query("SELECT DISTINCT new com.application.mainapp.dto.platformorder.PlatformOrderCourseResponseDTO(cc.course.courseID, cc.course.name, od.coursePrice, COUNT(od), SUM(od.coursePrice)) " +
            "FROM PlatformOrder po " +
            "JOIN OrderDetails od ON po.platformOrderID = od.platformOrder.platformOrderID " +
            "JOIN CourseCode cc ON od.orderDetailsID = cc.orderDetails.orderDetailsID " +
            "WHERE po.platformOrderID = :orderId " +
            "GROUP BY cc.course.courseID, cc.course.name, od.coursePrice " +
            "ORDER BY cc.course.courseID")
    List<PlatformOrderCourseResponseDTO> findPlatformOrderCourseResponseDTObyPlatformOrderId(@Param("orderId") long orderId);


    @Query("SELECT new com.application.mainapp.dto.platformorder.PlatformOrderCourseCodeInfoResponseDTO(cc.courseCodeID, cc.accessCode, " +
            "CASE WHEN cc.activeCode IS NULL THEN false ELSE true END) " +
            "FROM PlatformOrder po " +
            "JOIN OrderDetails od ON po.platformOrderID = od.platformOrder.platformOrderID " +
            "JOIN CourseCode cc ON od.orderDetailsID = cc.orderDetails.orderDetailsID " +
            "WHERE po.platformOrderID = :orderId AND cc.course.courseID = :courseId AND po.individualUser.platformUserID = :userId")
    List<PlatformOrderCourseCodeInfoResponseDTO> findPlatformOrderCourseCodeInfoResponseDTObyPlatformOrderIdAndCourseIdAndUserId(@Param("orderId") long orderId, @Param("courseId") long courseId, @Param(("userId")) long userId);

    @Query("SELECT new com.application.mainapp.dto.platformorder.PlatformOrderCourseCodeInfoResponseDTO(cc.courseCodeID, cc.accessCode, " +
            "CASE WHEN cc.activeCode IS NULL THEN false ELSE true END) " +
            "FROM PlatformOrder po " +
            "JOIN OrderDetails od ON po.platformOrderID = od.platformOrder.platformOrderID " +
            "JOIN CourseCode cc ON od.orderDetailsID = cc.orderDetails.orderDetailsID " +
            "WHERE po.platformOrderID = :orderId AND cc.course.courseID = :courseId")
    List<PlatformOrderCourseCodeInfoResponseDTO> findPlatformOrderCourseCodeInfoResponseDTObyPlatformOrderIdAndCourseId(@Param("orderId") long orderId, @Param("courseId") long courseId);
}
