package org.example.cmpe202_final;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryCourseStore {
    private Map<Integer, Course> courses = new HashMap<>();
    private static final InMemoryCourseStore INSTANCE = new InMemoryCourseStore();

    public static InMemoryCourseStore getInstance() {
        return INSTANCE;
    }

    public Optional<Course> findCourseById(int id) {
        return Optional.ofNullable(courses.get(id));
    }

    public void saveCourse(Course course) {
        courses.put(course.getId(), course);
    }
}