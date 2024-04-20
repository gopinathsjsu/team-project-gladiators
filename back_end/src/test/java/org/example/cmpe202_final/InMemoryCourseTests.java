package org.example.cmpe202_final;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;

public class InMemoryCourseTests {
    private InMemoryCourseStore courseStore;

    @BeforeEach
    void setUp() {
        courseStore = InMemoryCourseStore.getInstance();


        courseStore.saveCourse(new Course(1, "Computer Science", "Introduction to CS", "Fall 2024", true,
                null, null, null, null, null));
    }

    @Test
    public void testFindCourseById_ExistingId() {
        Optional<Course> result = courseStore.findCourseById(1);
        assertTrue(result.isPresent(), "Course should be found with ID 1");
        assertEquals("Computer Science", result.get().getName(), "Course name should match");
    }

    @Test
    public void testFindCourseById_NonExistingId() {
        Optional<Course> result = courseStore.findCourseById(999);
        assertFalse(result.isPresent(), "Course should not be found with non-existing ID");
    }

    @Test
    public void testSaveCourse_NewCourse() {
        Course newCourse = new Course(2, "Mathematics", "Algebra 101", "Spring 2025", true, null, null, null, null, null);
        courseStore.saveCourse(newCourse);
        Optional<Course> result = courseStore.findCourseById(2);
        assertTrue(result.isPresent(), "New course should be saved and found");
        assertEquals("Mathematics", result.get().getName(), "The saved course name should match");
    }
}