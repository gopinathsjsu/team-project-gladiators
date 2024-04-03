package org.example.cmpe202_final;

import java.util.Date;

public class Announcement {
    private int id;
    private String message;
    private Date date;
    private Course course;

    // Constructors, getters, and setters
    public Announcement(int id, String message, Date date, Course course) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.course = course;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
