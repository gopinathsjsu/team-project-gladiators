package org.example.cmpe202_final.model.courses;

import org.example.cmpe202_final.model.course.Course;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseTest {

    private final Course course = new Course();

    @Test
    public void testGettersAndSetters() {
        // Set up test data
        String id = "123";
        String instructor = "John Doe";
        Set<String> enrolledStudents = new HashSet<>();
        enrolledStudents.add("Alice");
        enrolledStudents.add("Bob");
        Set<String> assignments = new HashSet<>();
        assignments.add("Assignment 1");
        assignments.add("Assignment 2");
        String semester = "Spring 2023";
        boolean isPublished = true;
        String name = "Introduction to Java";
        String description = "This course covers the basics of Java programming.";

        // Set values using setters
        course.setId(id);
        course.setInstructor(instructor);
        course.setEnrolledStudents(enrolledStudents);
        course.setAssignments(assignments);
        course.setSemester(semester);
        course.setPublished(isPublished);
        course.setName(name);
        course.setDescription(description);

        // Test getters
        assertEquals(id, course.getId());
        assertEquals(instructor, course.getInstructor());
        assertEquals(enrolledStudents, course.getEnrolledStudents());
        assertEquals(assignments, course.getAssignments());
        assertEquals(semester, course.getSemester());
        assertEquals(isPublished, course.isPublished());
        assertEquals(name, course.getName());
        assertEquals(description, course.getDescription());
    }
}
