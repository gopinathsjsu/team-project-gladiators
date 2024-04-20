package org.example.cmpe202_final.controller.grade;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.course.Grade;
import org.example.cmpe202_final.model.course.StudentGradeDTO;
import org.example.cmpe202_final.service.course.GradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@AllArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @GetMapping("/assignment/{assignmentId}")
    public List<StudentGradeDTO> getGradesByAssignmentId(@PathVariable String assignmentId) {
        return gradeService.getGradesWithStudentNamesByAssignmentId(assignmentId);
    }
}