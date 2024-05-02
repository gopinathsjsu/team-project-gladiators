package org.example.cmpe202_final.service.course;

import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.repository.courses.CourseRepository;
import org.example.cmpe202_final.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Set<String> studentIds = new HashSet<>(Arrays.asList("1", "2"));
        Course course = new Course(); // Assuming Course has a constructor or a setter to set enrolledStudents
        course.setEnrolledStudents(studentIds);
        List<User> expectedUsers = Arrays.asList(
                new User("1", "Alice", "STUDENT", null, null, null, null, null),
                new User("2", "Bob", "STUDENT", null, null, null, null, null)
        );

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(course));
        when(userRepository.findStudentsByEnrolledIds(studentIds)).thenReturn(expectedUsers);

        // Execute
        List<User> actualUsers = courseService.findStudentsByCourseId(courseId);

        // Verify
        assertNotNull(actualUsers, "The returned list should not be null");
        assertFalse(actualUsers.isEmpty(), "The returned list should not be empty");
        assertEquals(2, actualUsers.size(), "The returned list size should match the expected mock");
        assertEquals(expectedUsers, actualUsers, "The returned list should match the expected users");
        verify(courseRepository).findById(courseId);
        verify(userRepository).findStudentsByEnrolledIds(studentIds);
    }

    @Test
    void testFindStudentsByCourseId_ReturnsEmptyList() {
        // Setup
        String courseId = "course456";
        Set<String> studentIds = new HashSet<>();
        Course course = new Course(); // Assuming Course has a method to set enrolledStudents
        course.setEnrolledStudents(studentIds);

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(course));
        when(userRepository.findStudentsByEnrolledIds(studentIds)).thenReturn(new ArrayList<>());

        // Execute
        List<User> actualUsers = courseService.findStudentsByCourseId(courseId);

        // Verify
        assertNotNull(actualUsers, "The returned list should not be null");
        assertTrue(actualUsers.isEmpty(), "The returned list should be empty");
        verify(courseRepository).findById(courseId);
        verify(userRepository).findStudentsByEnrolledIds(studentIds);
    }
}
