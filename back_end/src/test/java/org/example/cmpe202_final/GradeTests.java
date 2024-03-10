package org.example.cmpe202_final;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GradeTests {

    private Course course;
    private Faculty faculty;
    private List<Student> students;
    private List<Content> contents;
    private List<Grade> grades;
    private List<Announcement> announcements;

    private Student student;

    private Grade grade;
    @BeforeEach
    void setUp() {
        // Creating test data
        faculty = new Faculty(1, "John Doe", "john.doe@example.com");
        students = new ArrayList<>();
        contents = new ArrayList<>();
        grades = new ArrayList<>();
        announcements = new ArrayList<>();
        course = new Course(1, "Math", "Mathematics course", "Spring 2024", true, faculty, students, contents, grades, announcements);
        student = new Student(1, "Student Jane", "studentjane@example.com");
        grade = new Grade(1, student, course, 95.0);
    }

    @Test
    public void testGetters() {
        assertEquals(1, grade.getId());
        assertEquals(student, grade.getStudent());
        assertEquals(course, grade.getCourse());
        assertEquals(95.0, grade.getValue());
    }

    @Test
    public void testSetters() {
        grade.setId(1);
        assertEquals(1, grade.getId());

        grade.setStudent(student);
        assertEquals(student, grade.getStudent());

        grade.setCourse(course);
        assertEquals(course, grade.getCourse());

        grade.setValue(95.0);
        assertEquals(95.0, grade.getValue());
    }
}