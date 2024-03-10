package org.example.cmpe202_final;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizTests {
    private Course course;
    private Faculty faculty;
    private List<Student> students;
    private List<Content> contents;
    private List<Grade> grades;
    private List<Announcement> announcements;

    @BeforeEach
    void setUp() {
        // Creating test data
        faculty = new Faculty(1, "John Doe", "john.doe@example.com");
        students = new ArrayList<>();
        contents = new ArrayList<>();
        grades = new ArrayList<>();
        announcements = new ArrayList<>();
        course = new Course(1, "Math", "Mathematics course", "Spring 2024", true, faculty, students, contents, grades, announcements);
    }

    @Test
    public void testConstructor() {
        List<String> questions = Arrays.asList("Question1", "Question2");
        Quiz quiz = new Quiz(1, "QuizContent", course, questions);

        assertEquals(1, quiz.getId());
        assertEquals("QuizContent", quiz.getContent());
        assertEquals(course, quiz.getCourse());
        assertEquals(questions, quiz.getQuestions());
    }

    @Test
    public void testGetQuestions() {
        List<String> questions = Arrays.asList("Question1", "Question2");
        Quiz quiz = new Quiz(1, "QuizContent", course, questions);

        List<String> retrievedQuestions = quiz.getQuestions();
        assertEquals(2, retrievedQuestions.size());
        assertTrue(retrievedQuestions.containsAll(questions));
    }

    @Test
    public void testSetQuestions() {
        Quiz quiz = new Quiz(1, "QuizContent", course, Arrays.asList("Question1", "Question2"));
        List<String> newQuestions = Arrays.asList("Question3", "Question4");

        quiz.setQuestions(newQuestions);

        List<String> retrievedQuestions = quiz.getQuestions();
        assertEquals(2, retrievedQuestions.size());
        assertTrue(retrievedQuestions.containsAll(newQuestions));
    }
}