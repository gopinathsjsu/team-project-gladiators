package org.example.cmpe202_final;
import java.util.List;
public class Quiz extends Content {
    private List<String> questions;

    // Constructor that initializes id, content, course, and questions
    public Quiz(int id, String content, Course course, List<String> questions) {
        super(id, content, course);
        this.questions = questions;
    }

    // Getter for questions
    public List<String> getQuestions() {
        return questions;
    }

    // Setter for questions
    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }
}