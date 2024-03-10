package org.example.cmpe202_final;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;

public class AnnouncementTests {

    @Test
    public void testAnnouncementConstructor() {
        Date now = new Date();
        Course course = new Course(1, "Course1", "Description", "Spring 2024", true, null, null, null, null, null);
        Announcement announcement = new Announcement(1, "Important", now, course);

        assertEquals(1, announcement.getId());
        assertEquals("Important", announcement.getMessage());
        assertEquals(now, announcement.getDate());
        assertEquals(course, announcement.getCourse());
    }

    @Test
    public void testSetAndGetId() {
        Announcement announcement = new Announcement(1, "Message", new Date(), null);
        announcement.setId(2);
        assertEquals(2, announcement.getId());
    }

    @Test
    public void testSetAndGetName() {
        Announcement announcement = new Announcement(1, "Message", new Date(), null);
        announcement.setMessage("New Message");
        assertEquals("New Message", announcement.getMessage());
    }

    @Test
    public void testSetAndGetDate() {
        Date newDate = new Date();
        Announcement announcement = new Announcement(1, "Message", new Date(), null);
        announcement.setDate(newDate);
        assertEquals(newDate, announcement.getDate());
    }

    @Test
    public void testSetAndGetCourse() {
        Course newCourse = new Course(2, "Course2", "Description2", "Fall 2024", false, null, null, null, null, null);
        Announcement announcement = new Announcement(1, "Message", new Date(), null);
        announcement.setCourse(newCourse);
        assertEquals(newCourse, announcement.getCourse());
    }
}