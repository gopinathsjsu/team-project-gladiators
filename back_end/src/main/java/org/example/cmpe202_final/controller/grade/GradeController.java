package org.example.cmpe202_final.controller.grade;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.course.GradeWithStudentName;
import org.example.cmpe202_final.service.course.GradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@AllArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @GetMapping("/assignment/{assignmentId}")
    public List<GradeWithStudentName> getGradesByAssignmentId(
            @PathVariable String assignmentId,
            @RequestParam(required = false) String studentId) {
        if (studentId != null) {
            // Perform action A when userId is passed
            return gradeService.getGradeWithStudentNameByAssignmentIdAndStudentId(assignmentId, studentId);
        } else {
            // Perform action B when userId is not passed
            return gradeService.getGradesWithStudentNamesByAssignmentId(assignmentId);
        }
    }
}