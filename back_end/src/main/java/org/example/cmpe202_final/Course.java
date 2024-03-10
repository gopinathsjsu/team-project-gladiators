package org.example.cmpe202_final;

import java.util.List;

public class Course {
    private int id;
    private String name;
    private String description;
    private String semester;
    private boolean isPublished;
    private Faculty faculty;
    private List<Student> students;
    private List<Content> contents;
    private List<Grade> grades;
    private List<Announcement> announcements;

    // Constructors, getters, and setters
    // Constructor
    public Course(int id, String name, String description, String semester, boolean isPublished, Faculty faculty, List<Student> students, List<Content> contents, List<Grade> grades, List<Announcement> announcements) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.semester = semester;
        this.isPublished = isPublished;
        this.faculty = faculty;
        this.students = students;
        this.contents = contents;
        this.grades = grades;
        this.announcements = announcements;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

}