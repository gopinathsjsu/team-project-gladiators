package org.example.cmpe202_final.model.announcement;

import org.junit.jupiter.api.Test;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnnouncementTests {

    @Test
    public void testNoArgsConstructor() {
        // Create an instance using no-args constructor
        Announcement announcement = new Announcement();

        // Verify that the object is not null
        assertNotNull(announcement);
    }

    @Test
    public void testAllArgsConstructor() {
        // Create an instance using all-args constructor
        Announcement announcement = new Announcement("1", "courseId", "title", "description");

        // Verify the values are correctly set
        assertEquals("1", announcement.getId());
        assertEquals("courseId", announcement.getCourseId());
        assertEquals("title", announcement.getTitle());
        assertEquals("description", announcement.getDescription());
    }

    @Test
    public void testGettersAndSetters() {
        // Create an instance
        Announcement announcement = new Announcement();

        // Set values using setters
        announcement.setId("1");
        announcement.setCourseId("courseId");
        announcement.setTitle("title");
        announcement.setDescription("description");

        // Verify the values are correctly retrieved using getters
        assertEquals("1", announcement.getId());
        assertEquals("courseId", announcement.getCourseId());
        assertEquals("title", announcement.getTitle());
        assertEquals("description", announcement.getDescription());
    }

    @Test
    public void testDocumentAnnotation() {
        // Check if the class is annotated with @Document and collection value is "announcements"
        Document documentAnnotation = Announcement.class.getAnnotation(Document.class);
        assertNotNull(documentAnnotation);
        assertEquals("announcements", documentAnnotation.collection());
    }

    @Test
    public void testIdAnnotation() {
        // Check if the id field is annotated with @Id
        Id idAnnotation = null;
        try {
            idAnnotation = Announcement.class.getDeclaredField("id").getAnnotation(Id.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        assertNotNull(idAnnotation);
    }
}
