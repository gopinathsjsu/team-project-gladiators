package org.example.cmpe202_final.model.assignment;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@NoArgsConstructor
@Document(collection = "assignments")
public class Assignment {
    @Id
    private String id;
    private Date dueDate;
    private String name;
    private ArrayList<String> submissions;
    private String course;
    private String link;

    public Assignment(String id, Date dueDate, String name, ArrayList<String> submissions, String course, String link) {
        this.id = id;
        this.dueDate = dueDate;
        this.name = name;
        this.submissions = submissions;
        this.course = course;
        this.link = link;
    }

    public String getCourse() {
        return course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(ArrayList<String> submissions) {
        this.submissions = submissions;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}