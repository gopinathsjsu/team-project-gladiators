package org.example.cmpe202_final.model.courses;


import org.example.cmpe202_final.model.course.Grade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradeTests {

    private final Grade grade = new Grade();

    @Test
    public void testGettersAndSetters() {
        // Set up test data
        String id = "123";
        int score = 85;
        String studentId = "456";
        String courseId = "789";
        String submissionLink ="www.test.com/123";

        // Set values using setters
        grade.setId(id);
        grade.setScore(score);
        grade.setStudentId(studentId);
        grade.setAssignmentId(courseId);
        grade.setSubmissionLink(submissionLink);

        // Test getters
        assertEquals(id, grade.getId());
        assertEquals(score, grade.getScore());
        assertEquals(studentId, grade.getStudentId());
        assertEquals(courseId, grade.getAssignmentId());
        assertEquals(submissionLink, grade.getSubmissionLink());

        Grade grade2 = new Grade(id, score, studentId, courseId, submissionLink);

        // Test getters
        assertEquals(id, grade2.getId());
        assertEquals(score, grade2.getScore());
        assertEquals(studentId, grade2.getStudentId());
        assertEquals(courseId, grade2.getAssignmentId());
        assertEquals(submissionLink, grade2.getSubmissionLink());
    }
}
