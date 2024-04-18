package org.example.cmpe202_final.repository.grades;

import org.example.cmpe202_final.model.course.Grade;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GradeRepository extends MongoRepository<Grade, String> {
}
