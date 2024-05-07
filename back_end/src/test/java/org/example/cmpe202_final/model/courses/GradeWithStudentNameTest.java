package org.example.cmpe202_final.model.courses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.cmpe202_final.model.course.GradeWithStudentName;
import org.junit.jupiter.api.Test;

public class GradeWithStudentNameTest {

    @Test
    public void testGetterAndSetter() {
        // Create an instance of GradeWithStudentName
        GradeWithStudentName grade = new GradeWithStudentName();

        // Test setting and getting the id
        grade.setId("123");
        assertEquals("123", grade.getId(), "The id should be set to 123.");

        // Test setting and getting the score
        grade.setScore(95);
        assertEquals(95, grade.getScore(), "The score should be set to 95.");

        // Test setting and getting the studentFirstName
        grade.setStudentFirstName("John");
        assertEquals("John", grade.getStudentFirstName(), "The first name should be set to John.");

        // Test setting and getting the studentLastName
        grade.setStudentLastName("Doe");
        assertEquals("Doe", grade.getStudentLastName(), "The last name should be set to Doe.");

        // Test setting and getting the studentId
        grade.setStudentId("s123");
        assertEquals("s123", grade.getStudentId(), "The student ID should be set to s123.");

        // Test setting and getting the submissionLink
        grade.setSubmissionLink("http://example.com");
        assertEquals("http://example.com", grade.getSubmissionLink(), "The submission link should be set to http://example.com.");
    }

    @Test
    public void testAllArgsConstructor() {
        // Create an instance of GradeWithStudentName using the all-args constructor
        GradeWithStudentName grade = new GradeWithStudentName(
                "123", 95, "John", "Doe", "s123", "http://example.com"
        );

        // Assert that each field is initialized correctly
        assertEquals("123", grade.getId(), "The id should be correctly initialized.");
        assertEquals(95, grade.getScore(), "The score should be correctly initialized.");
        assertEquals("John", grade.getStudentFirstName(), "The first name should be correctly initialized.");
        assertEquals("Doe", grade.getStudentLastName(), "The last name should be correctly initialized.");
        assertEquals("s123", grade.getStudentId(), "The student ID should be correctly initialized.");
        assertEquals("http://example.com", grade.getSubmissionLink(), "The submission link should be correctly initialized.");
    }
}
