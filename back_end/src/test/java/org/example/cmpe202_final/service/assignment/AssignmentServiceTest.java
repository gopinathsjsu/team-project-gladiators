package org.example.cmpe202_final.service.assignment;

import org.example.cmpe202_final.model.assignment.Assignment;
import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.repository.assignment.AssignmentRepository;
import org.example.cmpe202_final.repository.courses.CourseRepository;
import org.example.cmpe202_final.repository.grades.GradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByCourse() {
        String courseId = "course123";
        Assignment assignment = new Assignment();
        assignment.setId("assignment123");
        ArrayList<Assignment> assignments = new ArrayList<>();
        assignments.add(assignment);

        when(assignmentRepository.findByCourse(courseId)).thenReturn(Optional.of(assignments));

        Optional<ArrayList<Assignment>> result = assignmentService.findByCourse(courseId);
        assertEquals(assignments, result.orElse(new ArrayList<>()));

        verify(assignmentRepository, times(1)).findByCourse(courseId);
    }

    @Test
    public void testAddItem() {
        String courseId = "course123";
        String assignmentId = "assignment123";
        Assignment assignment = new Assignment();
        assignment.setId(assignmentId);
        assignment.setCourse(courseId);

        Course course = new Course();
        Set<String> enrolledStudents = Set.of("student1", "student2");
        course.setEnrolledStudents(enrolledStudents);

        when(assignmentRepository.save(assignment)).thenReturn(assignment);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Assignment result = assignmentService.addItem(assignment);
        assertEquals(assignment, result);

        verify(assignmentRepository, times(1)).save(assignment);
        verify(courseRepository, times(1)).findById(courseId);
        verify(gradeRepository, times(1)).saveAll(any());
    }

    @Test
    @Disabled
    public void testAddItemCourseNotFound() {
        Assignment assignment = new Assignment();
        assignment.setId("someId");
        assignment.setCourse("nonexistentCourseId");

        when(courseRepository.findById("nonexistentCourseId")).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            assignmentService.addItem(assignment);
        });

        assertEquals("Course not found for ID: nonexistentCourseId", thrown.getMessage());
        verify(courseRepository, times(1)).findById("nonexistentCourseId");
    }
}