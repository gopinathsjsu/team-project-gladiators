package org.example.cmpe202_final;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AssignmentTests {

    @Test
    public void testAssignmentConstructor() {
        Course course = new Course(1, "Course1", "Description", "Spring 2024", true, null, null, null, null, null);
        Assignment assignment = new Assignment(1, "Content", course, "2022-12-01", 100);

        assertEquals(1, assignment.getId());
        assertEquals("Content", assignment.getContent());
        assertEquals(course, assignment.getCourse());
        assertEquals("2022-12-01", assignment.getDueDate());
        assertEquals(100, assignment.getTotalMarks());
    }

    @Test
    public void testSetAndGetId() {
        Assignment assignment = new Assignment(1, "Content", null, "2022-12-01", 100);
        assignment.setId(2);
        assertEquals(2, assignment.getId());
    }

    @Test
    public void testSetAndGetContent() {
        Assignment assignment = new Assignment(1, "Content", null, "2022-12-01", 100);
        assignment.setContent("New Content");
        assertEquals("New Content", assignment.getContent());
    }

    @Test
    public void testSetAndGetCourse() {
        Course course = new Course(1, "Course1", "Description", "Spring 2024", true, null, null, null, null, null);
        Assignment assignment = new Assignment(1, "Content", null, "2022-12-01", 100);
        assignment.setCourse(course);
        assertEquals(course, assignment.getCourse());
    }

    @Test
    public void testSetAndGetDueDate() {
        Assignment assignment = new Assignment(1, "Content", null, "2022-12-01", 100);
        assignment.setDueDate("2022-12-02");
        assertEquals("2022-12-02", assignment.getDueDate());
    }

    @Test
    public void testSetAndGetTotalMarks() {
        Assignment assignment = new Assignment(1, "Content", null, "2022-12-01", 100);
        assignment.setTotalMarks(95);
        assertEquals(95, assignment.getTotalMarks());
    }
}