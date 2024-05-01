package org.example.cmpe202_final.service.course;

import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.model.user.UserType;
import org.example.cmpe202_final.repository.courses.CourseRepository;
import org.example.cmpe202_final.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

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

    @Test
    void testFindStudentsByCourseId_ReturnsStudents() {
        // Setup
        String courseId = "course123";
        List<User> expectedUsers = Arrays.asList(
                new User("1", "Alice", "STUDENT", null, null, null, null, null),
                new User("2", "Bob", "STUDENT", null, null, null, null, null)
        );

        // Mock the repository call to ensure no actual database access occurs
        when(userRepository.findByCourseId(courseId)).thenReturn(expectedUsers);

        // Execute
        // This line does not call the actual repository; it uses the mocked data above
        List<User> actualUsers = courseService.findStudentsByCourseId(courseId);

        // Verify
        // Check that the returned list matches expected mock data
        assertNotNull(actualUsers, "The returned list should not be null");
        assertFalse(actualUsers.isEmpty(), "The returned list should not be empty");
        assertEquals(2, actualUsers.size(), "The returned list size should match the expected mock");
        assertEquals(expectedUsers, actualUsers, "The returned list should match the expected users");
        // Ensure the repository method was called with the correct parameter
        verify(userRepository).findByCourseId(courseId);
    }

    @Test
    void testFindStudentsByCourseId_ReturnsEmptyList() {
        // Setup
        String courseId = "course456";
        // Ensure the method returns an empty list for this course ID, simulating no students found
        when(userRepository.findByCourseId(courseId)).thenReturn(new ArrayList<>());

        // Execute
        // This line only tests the response to the simulated condition above
        List<User> actualUsers = courseService.findStudentsByCourseId(courseId);

        // Verify
        // Validate handling of empty data
        assertNotNull(actualUsers, "The returned list should not be null");
        assertTrue(actualUsers.isEmpty(), "The returned list should be empty");
        // Confirm the method call to userRepository was made correctly
        verify(userRepository).findByCourseId(courseId);
    }
}
