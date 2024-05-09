package org.example.cmpe202_final.service.grade;

import org.example.cmpe202_final.service.course.GradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.cmpe202_final.model.course.Grade;
import org.example.cmpe202_final.model.course.GradeWithStudentName;
import org.example.cmpe202_final.repository.grades.CustomGradeRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GradeServiceTest {

    @Mock
    private CustomGradeRepository customGradeRepository;

    @InjectMocks
    private GradeService gradeService;

    @Test
    public void testGetGradesWithStudentNamesByAssignmentId() {
        // Arrange
        String assignmentId = "assign1";
        String userId = "user1";
        List<GradeWithStudentName> expectedGrades = Arrays.asList(
                new GradeWithStudentName("1", 90, "John", "Doe", "s123", "link")
        );
        when(customGradeRepository.getGradesWithStudentNamesByAssignmentId(assignmentId, userId)).thenReturn(expectedGrades);

        // Act
        List<GradeWithStudentName> result = gradeService.getGradesWithStudentNamesByAssignmentId(assignmentId, userId);

        // Assert
        assertEquals(expectedGrades, result);
        verify(customGradeRepository).getGradesWithStudentNamesByAssignmentId(assignmentId, userId);
    }

    @Test
    public void testUpdateGrade() {
        // Arrange
        GradeWithStudentName gradeWithStudentName = new GradeWithStudentName("1", 85, "Jane", "Doe", "s234", "link");
        Grade expectedGrade = new Grade("1", 85, "s234", "a234", "link");
        when(customGradeRepository.updateGrade(gradeWithStudentName)).thenReturn(expectedGrade);

        // Act
        Grade result = gradeService.updateGrade(gradeWithStudentName);

        // Assert
        assertEquals(expectedGrade, result);
        verify(customGradeRepository).updateGrade(gradeWithStudentName);
    }
}
