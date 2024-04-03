package org.example.cmpe202_final;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTests {

    private Course course;
    private Faculty faculty;
    private List<Student> students;
    private List<Content> contents;
    private List<Grade> grades;
    private List<Announcement> announcements;

    @BeforeEach
    public void setup() {
        faculty = new Faculty(1, "Faculty John", "facultyjohn@example.com"); // Assuming Faculty class exists
        students = new ArrayList<>();
        contents = new ArrayList<>();
        grades = new ArrayList<>();
        announcements = new ArrayList<>();
        course = new Course(1, "Course Name", "Course Description", "Fall 2021", true, faculty, students, contents, grades, announcements);
    }

    @Test
    public void testGetters() {
        assertEquals(1, course.getId());
        assertEquals("Course Name", course.getName());
        assertEquals("Course Description", course.getDescription());
        assertEquals("Fall 2021", course.getSemester());
        assertTrue(course.isPublished());
        assertEquals(faculty, course.getFaculty());
        assertEquals(students, course.getStudents());
        assertEquals(contents, course.getContents());
        assertEquals(grades, course.getGrades());
        assertEquals(announcements, course.getAnnouncements());
    }

    @Test
    public void testSetters() {
        Faculty newFaculty = new Faculty(1, "Faculty John", "facultyjohn@example.com"); // Assuming Faculty class exists
        List<Student> newStudents = new ArrayList<>();
        List<Content> newContents = new ArrayList<>();
        List<Grade> newGrades = new ArrayList<>();
        List<Announcement> newAnnouncements = new ArrayList<>();

        course.setId(2);
        assertEquals(2, course.getId());

        course.setName("New Course Name");
        assertEquals("New Course Name", course.getName());

        course.setDescription("New Description");
        assertEquals("New Description", course.getDescription());

        course.setSemester("Spring 2022");
        assertEquals("Spring 2022", course.getSemester());

        course.setPublished(false);
        assertFalse(course.isPublished());

        course.setFaculty(newFaculty);
        assertEquals(newFaculty, course.getFaculty());

        course.setStudents(newStudents);
        assertEquals(newStudents, course.getStudents());

        course.setContents(newContents);
        assertEquals(newContents, course.getContents());

        course.setGrades(newGrades);
        assertEquals(newGrades, course.getGrades());

        course.setAnnouncements(newAnnouncements);
        assertEquals(newAnnouncements, course.getAnnouncements());
    }
}