package org.example.cmpe202_final.service;

import org.example.cmpe202_final.model.Notification;
import org.example.cmpe202_final.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllNotifications() {
        Notification notification1 = new Notification("Course 1", "Event 1", "1", LocalDateTime.now().minusDays(1), "Type A");
        Notification notification2 = new Notification("Course 2", "Event 2", "2", LocalDateTime.now(), "Type B");
        List<Notification> expectedNotifications = Arrays.asList(notification2, notification1);

        when(notificationRepository.findAllByOrderByDateDesc()).thenReturn(expectedNotifications);

        List<Notification> actualNotifications = notificationService.getAllNotifications();

        assertThat(actualNotifications).isEqualTo(expectedNotifications);
        verify(notificationRepository).findAllByOrderByDateDesc();
    }

    @Test
    public void testInsertNotification() {
        Notification newNotification = new Notification("Course 3", "Event 3", "3", LocalDateTime.now(), "Type C");

        notificationService.insertNotification(newNotification);

        verify(notificationRepository).save(newNotification);
    }
}
