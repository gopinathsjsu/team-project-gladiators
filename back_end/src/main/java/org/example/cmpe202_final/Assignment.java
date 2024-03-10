package org.example.cmpe202_final;

public class Assignment extends Content {
    private String dueDate;
    private int totalMarks;

    // Constructor that initializes id, content, course, dueDate, and totalMarks
    public Assignment(int id, String content, Course course, String dueDate, int totalMarks) {
        super(id, content, course);
        this.dueDate = dueDate;
        this.totalMarks = totalMarks;
    }

    // Getter for dueDate
    public String getDueDate() {
        return dueDate;
    }

    // Setter for dueDate
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    // Getter for totalMarks
    public int getTotalMarks() {
        return totalMarks;
    }

    // Setter for totalMarks
    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }
}