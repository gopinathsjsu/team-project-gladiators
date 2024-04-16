package org.example.cmpe202_final.model.notifications;

import org.example.cmpe202_final.model.notification.Notification;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void testConstructorAndGetter() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification = new Notification("Math101", "Exam", "E1", now, "INFO");

        assertAll("constructor",
                () -> assertEquals("Math101", notification.getCourseName()),
                () -> assertEquals("Exam", notification.getEventTitle()),
                () -> assertEquals("E1", notification.getEventId()),
                () -> assertEquals(now, notification.getDate()),
                () -> assertEquals("INFO", notification.getType())
        );
    }

    @Test
    void testSetterAndGetter() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification = new Notification();
        notification.setCourseName("Math101");
        notification.setEventTitle("Exam");
        notification.setEventId("E1");
        notification.setDate(now);
        notification.setType("INFO");

        assertAll("setter and getter",
                () -> assertEquals("Math101", notification.getCourseName()),
                () -> assertEquals("Exam", notification.getEventTitle()),
                () -> assertEquals("E1", notification.getEventId()),
                () -> assertEquals(now, notification.getDate()),
                () -> assertEquals("INFO", notification.getType())
        );
    }
}