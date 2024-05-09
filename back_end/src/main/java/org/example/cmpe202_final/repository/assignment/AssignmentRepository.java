package org.example.cmpe202_final.repository.assignment;

import org.example.cmpe202_final.model.assignment.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    Optional<ArrayList<Assignment>> findByCourse(String course);
}
