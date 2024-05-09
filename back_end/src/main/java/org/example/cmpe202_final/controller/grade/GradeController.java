package org.example.cmpe202_final.controller.grade;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.course.Grade;
import org.example.cmpe202_final.model.course.GradeWithStudentName;
import org.example.cmpe202_final.service.course.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @RequestParam String userId) {
        return gradeService.getGradesWithStudentNamesByAssignmentId(assignmentId, userId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateGrade(@RequestBody GradeWithStudentName gradeWithStudentName) {
        try {
            Grade updatedGrade = gradeService.updateGrade(gradeWithStudentName);
            return ResponseEntity.ok(updatedGrade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating grade: " + e.getMessage());
        }
    }
}