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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private UserRepository userRepository;

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

    @Test
    void testFindStudentsByCourseName_WithResults() {
        // Setup
        String courseName = "Software Engineering";
        Set<String> enrolledStudents = new HashSet<>(Arrays.asList("John", "Jane"));

        Course course = new Course();
        course.setName(courseName);
        course.setEnrolledStudents(enrolledStudents);

        when(courseRepository.findByName(courseName)).thenReturn(Arrays.asList(course));
        when(userRepository.findByFirstNameIn(enrolledStudents)).thenReturn(Arrays.asList(
                new User("user1", "password1", UserType.STUDENT.name(), "John", "Doe"),
                new User("user2", "password2", UserType.STUDENT.name(), "Jane", "Doe")
        ));

        // Execution
        List<User> result = courseService.findStudentsByCourseName(courseName);

        // Assertions
        assertEquals(2, result.size());
        assertTrue(result.stream().map(User::getFirstName).collect(Collectors.toSet()).containsAll(Arrays.asList("John", "Jane")));

        // Verify
        verify(courseRepository).findByName(courseName);
        verify(userRepository).findByFirstNameIn(enrolledStudents);
    }

    @Test
    void testFindStudentsByCourseName_NoCoursesFound() {
        // Setup
        String courseName = "Nonexistent Course";
        when(courseRepository.findByName(courseName)).thenReturn(Arrays.asList());

        // Execution
        List<User> result = courseService.findStudentsByCourseName(courseName);

        // Assertions
        assertTrue(result.isEmpty());

        // Verify
        verify(courseRepository).findByName(courseName);
        verify(userRepository, never()).findByFirstNameIn(any());
    }

    @Test
    void testFindStudentsByCourseName_CoursesWithNoStudents() {
        // Setup
        String courseName = "Lonely Course";
        Course course = new Course();
        course.setName(courseName);
        course.setEnrolledStudents(new HashSet<>());

        when(courseRepository.findByName(courseName)).thenReturn(Arrays.asList(course));

        // Execution
        List<User> result = courseService.findStudentsByCourseName(courseName);

        // Assertions
        assertTrue(result.isEmpty());

        // Verify
        verify(courseRepository).findByName(courseName);
        verify(userRepository, never()).findByFirstNameIn(any());
    }
}
