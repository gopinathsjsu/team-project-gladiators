package org.example.cmpe202_final.repository.grades;

import org.example.cmpe202_final.model.course.StudentGradeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomGradeRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<StudentGradeDTO> getGradesWithStudentNames() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("students", "studentId", "id", "student"),
                Aggregation.unwind("student"),
                Aggregation.project("id", "score", "studentId")
                        .and("student.name").as("studentName")
        );

        AggregationResults<StudentGradeDTO> results = mongoTemplate.aggregate(
                aggregation, "grades", StudentGradeDTO.class
        );

        return results.getMappedResults();
    }
}
