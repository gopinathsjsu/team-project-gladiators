package org.example.cmpe202_final.repository;

import org.example.cmpe202_final.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        // Ensure the repository is empty before each test
        notificationRepository.deleteAll();

        // Prepare test data
        Notification notification1 = new Notification("1", "Course 1", "Event 1", "1", LocalDateTime.now().minusDays(1), "Type A");
        Notification notification2 = new Notification("2", "Course 2", "Event 2", "2", LocalDateTime.now(), "Type B");
        Notification notification3 = new Notification("3", "Course 3", "Event 3", "3", LocalDateTime.now().minusDays(2), "Type A");

        // Save test notifications
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);
    }

    @Test
    void testFindAllByOrderByDate() {
        List<Notification> notifications = notificationRepository.findAllByOrderByDateDesc();

        assertThat(notifications).isNotNull();
        assertThat(notifications.size()).isEqualTo(3);

        // Verify the order is correct based on date
        LocalDateTime previousDate = LocalDateTime.MAX;
        for (Notification notification : notifications) {
            assertThat(notification.getDate()).isBeforeOrEqualTo(previousDate);
            previousDate = notification.getDate();
        }
    }
}
