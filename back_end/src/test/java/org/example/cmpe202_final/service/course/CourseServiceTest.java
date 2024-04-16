package org.example.cmpe202_final.service.course;

import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.repository.courses.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testFindAllCourses() {
        // Mock repository behavior
        Course course1 = new Course("1", "John Doe", null, null, null, false, "Course 1", "Description 1");
        Course course2 = new Course("2", "Jane Smith", null, null, null, false, "Course 2", "Description 2");
        List<Course> courses = Arrays.asList(course1, course2);
        when(courseRepository.findAllBy()).thenReturn(courses);

        // Test service method
        List<Course> result = courseService.findAllCourses();
        assertEquals(courses.size(), result.size());
        assertEquals(courses, result);
    }

    @Test
    public void testFindByEnrolledStudent() {
        // Mock repository behavior
        String studentId = "123";
        Course course1 = new Course("1", "John Doe", null, null, null, false, "Course 1", "Description 1");
        Course course2 = new Course("2", "Jane Smith", null, null, null, false, "Course 2", "Description 2");
        List<Course> courses = Arrays.asList(course1, course2);
        when(courseRepository.findByEnrolledStudent(studentId)).thenReturn(courses);

        // Test service method
        List<Course> result = courseService.findByEnrolledStudent(studentId);
        assertEquals(courses.size(), result.size());
        assertEquals(courses, result);
    }

    @Test
    public void testFindByInstructor() {
        // Mock repository behavior
        String instructor = "John Doe";
        Course course1 = new Course("1", instructor, null, null, null, false, "Course 1", "Description 1");
        List<Course> courses = Arrays.asList(course1);
        when(courseRepository.findByInstructor(instructor)).thenReturn(courses);

        // Test service method
        List<Course> result = courseService.findByInstructor(instructor);
        assertEquals(courses.size(), result.size());
        assertEquals(courses, result);
    }
}
