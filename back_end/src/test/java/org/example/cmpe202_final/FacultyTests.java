package org.example.cmpe202_final;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FacultyTests {

    @Test
    public void testFacultyConstructor() {
        Faculty faculty = new Faculty(1, "Faculty John", "facultyjohn@example.com");
        assertEquals(1, faculty.getId());
        assertEquals("Faculty John", faculty.getName());
        assertEquals("facultyjohn@example.com", faculty.getEmail());
    }

    @Test
    public void testSetAndGetId() {
        Faculty faculty = new Faculty(1, "Faculty John", "facultyjohn@example.com");
        faculty.setId(1);
        assertEquals(1, faculty.getId());
    }

    @Test
    public void testSetAndGetName() {
        Faculty faculty = new Faculty(1, "Faculty John", "facultyjohn@example.com");
        faculty.setName("Faculty John");
        assertEquals("Faculty John", faculty.getName());
    }

    @Test
    public void testSetAndGetEmail() {
        Faculty faculty = new Faculty(1, "Faculty John", "facultyjohn@example.com");
        faculty.setEmail("facultyjohn@example.com");
        assertEquals("facultyjohn@example.com", faculty.getEmail());
    }
}