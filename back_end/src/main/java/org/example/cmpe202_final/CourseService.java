package org.example.cmpe202_final;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CourseService {



    private final InMemoryCourseStore courseStore;

    // Constructor for dependency injection
    public CourseService(InMemoryCourseStore courseStore) {
        this.courseStore = courseStore;
    }

    public Optional<Course> findCourseById(int id) {
        return courseStore.findCourseById(id);
    }

    public void addCourse(Course course) {
        courseStore.saveCourse(course);
    }
}