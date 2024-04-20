package org.example.cmpe202_final.repository.grades;

import org.example.cmpe202_final.model.course.StudentGradeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomGradeRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<StudentGradeDTO> getGradesWithStudentNamesByAssignmentId(String assignmentId) {
        System.out.println(assignmentId);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("assignmentId").is(assignmentId)),
                Aggregation.lookup("users", "studentId", "_id", "student"),
                Aggregation.unwind("student"),
                Aggregation.project("score", "studentId")
                        .and("_id").as("gradeId")
                        .and("student.firstName").as("studentFirstName")
                        .and("student.lastName").as("studentLastName")
        );

        AggregationResults<StudentGradeDTO> results = mongoTemplate.aggregate(
                aggregation, "grades", StudentGradeDTO.class
        );

        return results.getMappedResults();
    }
}
