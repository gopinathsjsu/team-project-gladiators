package org.example.cmpe202_final.repository.Student;

import org.example.cmpe202_final.model.student.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
}