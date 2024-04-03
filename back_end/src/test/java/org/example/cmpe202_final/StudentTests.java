package org.example.cmpe202_final;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StudentTests {

    @Test
    public void testStudentConstructor() {
        Student student = new Student(1, "Student Jane", "studentjane@example.com");
        assertEquals(1, student.getId());
        assertEquals("Student Jane", student.getName());
        assertEquals("studentjane@example.com", student.getEmail());
    }

    @Test
    public void testSetAndGetId() {
        Student student = new Student(1, "Student Jane", "studentjane@example.com");
        student.setId(1);
        assertEquals(1, student.getId());
    }

    @Test
    public void testSetAndGetName() {
        Student student = new Student(1, "Student Jane", "studentjane@example.com");
        student.setName("Student Jane");
        assertEquals("Student Jane", student.getName());
    }

    @Test
    public void testSetAndGetEmail() {
        Student student = new Student(1, "Student Jane", "studentjane@example.com");
        student.setEmail("studentjane@example.com");
        assertEquals("studentjane@example.com", student.getEmail());
    }
}