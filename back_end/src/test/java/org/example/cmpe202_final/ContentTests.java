package org.example.cmpe202_final;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;



public class ContentTests {

    public static class TestableContent extends Content {
        public TestableContent(int id, String content, Course course) {
            super(id, content, course);
        }
    }

    @Test
    public void testContentConstructor() {
        Course course = new Course(1, "Course1", "Description", "Spring 2024", true, null, null, null, null, null);
        TestableContent content = new TestableContent(1, "Content1", course);

        assertEquals(1, content.getId());
        assertEquals("Content1", content.getContent());
        assertEquals(course, content.getCourse());
    }

    @Test
    public void testSetAndGetId() {
        Course course = new Course(1, "Course1", "Description", "Spring 2024", true, null, null, null, null, null);
        TestableContent content = new TestableContent(1, "Content1", course);
        content.setId(2);

        assertEquals(2, content.getId());
    }

    @Test
    public void testSetAndGetContent() {
        Course course = new Course(1, "Course1", "Description", "Spring 2024", true, null, null, null, null, null);
        TestableContent content = new TestableContent(1, "Content1", course);
        content.setContent("Content2");

        assertEquals("Content2", content.getContent());
    }

    @Test
    public void testSetAndGetCourse() {
        Course course1 = new Course(1, "Course1", "Description", "Spring 2024", true, null, null, null, null, null);
        Course course2 = new Course(2, "Course2", "Description2", "Fall 2024", false, null, null, null, null, null);
        TestableContent content = new TestableContent(1, "Content1", course1);
        content.setCourse(course2);

        assertEquals(course2, content.getCourse());
    }
}