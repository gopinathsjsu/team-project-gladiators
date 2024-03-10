package org.example.cmpe202_final;

public class Grade {
    private int id;
    private Student student;
    private Course course;
    private double value;

    // Constructor
    public Grade(int id, Student student, Course course, double value) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.value = value;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for student
    public Student getStudent() {
        return student;
    }

    // Setter for student
    public void setStudent(Student student) {
        this.student = student;
    }

    // Getter for course
    public Course getCourse() {
        return course;
    }

    // Setter for course
    public void setCourse(Course course) {
        this.course = course;
    }

    // Getter for value
    public double getValue() {
        return value;
    }

    // Setter for value
    public void setValue(double value) {
        this.value = value;
    }
}