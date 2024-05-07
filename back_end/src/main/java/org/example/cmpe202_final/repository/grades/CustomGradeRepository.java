package org.example.cmpe202_final.repository.grades;

import org.example.cmpe202_final.model.course.Grade;
import org.example.cmpe202_final.model.course.GradeWithStudentName;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomGradeRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    public List<GradeWithStudentName> getGradesWithStudentNamesByAssignmentId(
            String assignmentId, String userId) {
        // Fetch user from UserService to get the 'type' attribute
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String userType = user.getType(); // Assuming 'type' is an attribute in User

            // Build aggregation with optional additional filtering based on user type
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("assignmentId").is(assignmentId)),
                    Aggregation.lookup("users", "studentId", "_id", "student"),
                    Aggregation.unwind("student"),
                    Aggregation.project("score", "studentId", "submissionLink")
                            .and("_id").as("gradeId")
                            .and("student.firstName").as("studentFirstName")
                            .and("student.lastName").as("studentLastName")
            );

            AggregationResults<GradeWithStudentName> results = mongoTemplate.aggregate(
                    aggregation, "grades", GradeWithStudentName.class
            );
            List<GradeWithStudentName> grades = results.getMappedResults();

            if (userType.equals("STUDENT")) {
               grades = grades.stream().filter(g -> g.getStudentId().equals(userId)).toList();
            }
            return  grades;
        }

        throw new IllegalArgumentException("User with ID " + userId + " not found.");
    }

    public Grade updateGrade(GradeWithStudentName gradeWithStudentName) {
        Query query = new Query(Criteria.where("_id").is(gradeWithStudentName.getId()));
        Update update = new Update();
        update.set("score", gradeWithStudentName.getScore());
        update.set("submissionLink", gradeWithStudentName.getSubmissionLink());

        Grade updatedGrade = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Grade.class);
        if (updatedGrade == null) {
            throw new IllegalArgumentException("No grade found with ID: " + gradeWithStudentName.getId());
        }
        return updatedGrade;
    }
}
