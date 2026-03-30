package com.application.mainapp.bootstrap;

import com.application.mainapp.model.*;
import com.application.mainapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderSeeder {

    private final PlatformOrderRepository platformOrderRepository;
    private final IndividualUserRepository individualUserRepository;
    private final CourseRepository courseRepository;
    private final CourseCodeRepository courseCodeRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ActiveCodeRepository activeCodeRepository;

    public void seed() {
        if (platformOrderRepository.count() > 0) {
            return;
        }

        IndividualUser testUser = individualUserRepository.findAll().stream()
                .filter(u -> "user1@test.com".equals(u.getEmail()))
                .findFirst()
                .orElse(null);

        if (testUser == null) {
            return;
        }

        List<Course> coursesToBuy = courseRepository.findAll().stream()
                .filter(c -> c.getCreator() == null || !(c.getCreator().getPlatformUserID() == testUser.getPlatformUserID()))
                .limit(3)
                .toList();

        if (coursesToBuy.size() < 3) {
            return;
        }

        PlatformOrder order = new PlatformOrder(Timestamp.from(Instant.now()), testUser);
        platformOrderRepository.save(order);

        for (int i = 0; i < coursesToBuy.size(); i++) {
            Course course = coursesToBuy.get(i);

            for (int j = 0; j < 2; j++) {
                CourseCode courseCode = new CourseCode(UUID.randomUUID().toString(), course);
                courseCodeRepository.save(courseCode);

                OrderDetails orderDetails = new OrderDetails(course.getPrice(), courseCode, order);
                orderDetails.setCoursePrice(course.getPrice());
                orderDetailsRepository.save(orderDetails);

                if (i < 2 && j == 0) {
                    ActiveCode activeCode = new ActiveCode(courseCode, testUser);
                    activeCodeRepository.save(activeCode);
                }
            }
        }
    }
}