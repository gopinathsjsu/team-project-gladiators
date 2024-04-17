package org.example.cmpe202_final.model.courses;

import org.example.cmpe202_final.model.course.Assignment;
import org.example.cmpe202_final.model.course.Grade;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssignmentTests {

    private final Assignment assignment = new Assignment();

    @Test
    public void testGettersAndSetters() {
        // Set up test data
        String id = "123";
        Date dueDate = new Date();
        String name = "Assignment 1";
        ArrayList<Grade> grades = new ArrayList<>();

        // Set values using setters
        assignment.setId(id);
        assignment.setDueDate(dueDate);
        assignment.setName(name);
        assignment.setGrades(grades);

        // Test getters
        assertEquals(id, assignment.getId());
        assertEquals(dueDate, assignment.getDueDate());
        assertEquals(name, assignment.getName());
        assertEquals(grades, assignment.getGrades());

        // Test Full Constructor
        Assignment assignment2 = new Assignment(id,dueDate,name,grades);
        assertEquals(id, assignment2.getId());
        assertEquals(dueDate, assignment2.getDueDate());
        assertEquals(name, assignment2.getName());
        assertEquals(grades, assignment2.getGrades());
    }
}

