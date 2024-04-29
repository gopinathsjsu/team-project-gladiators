package org.example.cmpe202_final.model.assignment;

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
        String course = "course";
        String link = "link";
        ArrayList<String> submissions = new ArrayList<>();

        // Set values using setters
        assignment.setId(id);
        assignment.setDueDate(dueDate);
        assignment.setName(name);
        assignment.setSubmissions(submissions);

        // Test getters
        assertEquals(id, assignment.getId());
        assertEquals(dueDate, assignment.getDueDate());
        assertEquals(name, assignment.getName());
        assertEquals(submissions, assignment.getSubmissions());

        // Test Full Constructor
        Assignment assignment2 = new Assignment(id,dueDate,name,submissions,course, link);
        assertEquals(id, assignment2.getId());
        assertEquals(dueDate, assignment2.getDueDate());
        assertEquals(name, assignment2.getName());
        assertEquals(submissions, assignment2.getSubmissions());
        assertEquals(course, assignment2.getCourse());
        assertEquals(link, assignment2.getLink());
    }
}

