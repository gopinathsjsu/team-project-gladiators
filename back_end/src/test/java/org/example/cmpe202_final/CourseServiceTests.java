package org.example.cmpe202_final;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class CourseServiceTests {

    @Mock
    private InMemoryCourseStore mockCourseStore;

    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Pass the mocked store to the service
        courseService = new CourseService(mockCourseStore);
    }

    @Test
    public void testFindCourseById_ExistingId() {
        Course expectedCourse = new Course(1, "Computer Science", "Intro to CS", "Fall 2024", true, null, null, null, null, null);
        when(mockCourseStore.findCourseById(1)).thenReturn(Optional.of(expectedCourse));

        Optional<Course> result = courseService.findCourseById(1);
        assertTrue(result.isPresent(), "Course should be found");
        assertEquals("Computer Science", result.get().getName(), "Course name should match expected");
    }

    @Test
    public void testFindCourseById_NonExistingId() {
        when(mockCourseStore.findCourseById(999)).thenReturn(Optional.empty());

        Optional<Course> result = courseService.findCourseById(999);
        assertFalse(result.isPresent(), "No course should be found with this ID");
    }

    @Test
    public void testAddCourse() {
        Course newCourse = new Course(2, "Mathematics", "Algebra 101", "Spring 2025", true, null, null, null, null, null);
        doNothing().when(mockCourseStore).saveCourse(any(Course.class));

        courseService.addCourse(newCourse);
        verify(mockCourseStore).saveCourse(newCourse);  // Verify that the courseStore saved the course
    }
}