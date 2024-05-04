package org.example.cmpe202_final.service.assignment;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.assignment.Assignment;
import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.model.course.Grade;
import org.example.cmpe202_final.repository.assignment.AssignmentRepository;
import org.example.cmpe202_final.repository.courses.CourseRepository;
import org.example.cmpe202_final.repository.grades.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AssignmentService {
    @Autowired
    private final AssignmentRepository assignmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GradeRepository gradeRepository;

    public Optional<ArrayList<Assignment>> findByCourse(String course){
        return assignmentRepository.findByCourse(course);
    }

    public Assignment addItem(Assignment assignment){

        // Save the assignment
        assignment = assignmentRepository.save(assignment);

        // Retrieve the course associated with the assignment
        Course course = courseRepository.findById(assignment.getCourse()).orElse(null);
        if (course == null) {
            throw new IllegalArgumentException("Course not found for ID: " + assignment.getCourse());
        }

        // Create a new grade for each student enrolled in the course
        List<Grade> grades = new ArrayList<>();
        for (String studentId : course.getEnrolledStudents()) {
            Grade grade = new Grade();
            grade.setId(UUID.randomUUID().toString());
            grade.setStudentId(studentId);
            grade.setAssignmentId(assignment.getId());
            grades.add(grade);
        }

        // Save all grades
        gradeRepository.saveAll(grades);

        return assignment;
    }
}
