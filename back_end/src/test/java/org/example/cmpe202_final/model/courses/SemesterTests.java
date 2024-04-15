package org.example.cmpe202_final.model.courses;

import org.example.cmpe202_final.model.course.Semester;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SemesterTests {

    private final Semester semester = new Semester();

    @Test
    public void testGettersAndSetters() {
        // Set up test data
        String id = "123";
        Date startDate = new Date();
        Date endDate = new Date();
        String name = "Spring 2023";

        // Set values using setters
        semester.setId(id);
        semester.setStartDate(startDate);
        semester.setEndDate(endDate);
        semester.setName(name);

        // Test getters
        assertEquals(id, semester.getId());
        assertEquals(startDate, semester.getStartDate());
        assertEquals(endDate, semester.getEndDate());
        assertEquals(name, semester.getName());
    }
}
