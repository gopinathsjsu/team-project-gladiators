package org.example.cmpe202_final.service.course;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.course.Grade;
import org.example.cmpe202_final.model.course.GradeWithStudentName;
import org.example.cmpe202_final.repository.grades.CustomGradeRepository;
import org.example.cmpe202_final.repository.grades.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GradeService {

    private final CustomGradeRepository customGradeRepository;
    private final GradeRepository gradeRepository;

    public List<GradeWithStudentName> getGradesWithStudentNamesByAssignmentId(String assignmentId, String userId){
        return customGradeRepository.getGradesWithStudentNamesByAssignmentId(assignmentId, userId);
    }

    public Grade updateGrade(GradeWithStudentName gradeWithStudentName) {
        return customGradeRepository.updateGrade(gradeWithStudentName);
    }

}
