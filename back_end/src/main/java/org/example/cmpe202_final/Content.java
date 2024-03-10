package org.example.cmpe202_final;

public abstract class Content {
    protected int id;
    protected String content;
    protected Course course;

    // Constructors, getters, and setters
    public Content(int id, String content, Course course) {
        this.id = id;
        this.content = content;
        this.course = course;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for content
    public String getContent() {
        return content;
    }

    // Setter for content
    public void setContent(String content) {
        this.content = content;
    }

    // Getter for course
    public Course getCourse() {
        return course;
    }

    // Setter for course
    public void setCourse(Course course) {
        this.course = course;
    }
}