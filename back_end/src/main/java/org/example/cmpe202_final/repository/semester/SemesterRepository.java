package org.example.cmpe202_final.repository.semester;

import org.example.cmpe202_final.model.course.Semester;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;

public interface SemesterRepository extends MongoRepository<Semester, String> {
    List<Semester> findAll();
}
