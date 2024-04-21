package org.example.cmpe202_final.service.course;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.course.StudentGradeDTO;
import org.example.cmpe202_final.repository.grades.CustomGradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GradeService {

        private final CustomGradeRepository gradeRepository;

        public List<StudentGradeDTO> getGradesWithStudentNamesByAssignmentId(String assignmentId){
            return gradeRepository.getGradesWithStudentNamesByAssignmentId(assignmentId);
        }
}