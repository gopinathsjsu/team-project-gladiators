package org.example.cmpe202_final.service.course;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.repository.courses.CourseRepository;
import org.example.cmpe202_final.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    public List<Course> findAllCourses(){
        return courseRepository.findAllBy();
    }

    public List<Course> findByEnrolledStudent(String studentId){
        return courseRepository.findByEnrolledStudent(studentId);
    }

    public List<Course> findByInstructor(String instructor){
        return courseRepository.findByInstructor(instructor);
    }

    public List<User> findStudentsByCourseName(String courseName) {
        // Fetch courses by name
        List<Course> courses = courseRepository.findByName(courseName);

        // Stream courses to extract unique names of enrolled students
        Set<String> studentNames = courses.stream()
                .map(Course::getEnrolledStudents)  // Get student names from each course
                .filter(Objects::nonNull)          // Ensure the set is not null
                .flatMap(Set::stream)              // Flatten all sets into one stream
                .collect(Collectors.toSet());      // Collect into a set to remove duplicates

        // Return an empty list if no student names are collected
        if (studentNames.isEmpty()) {
            return new ArrayList<>();
        }

        // Use the new repository method to find all users by their names
        return userRepository.findByFirstNameIn(studentNames);
    }
}
