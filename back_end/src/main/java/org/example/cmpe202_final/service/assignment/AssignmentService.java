package org.example.cmpe202_final.service.assignment;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.assignment.Assignment;
import org.example.cmpe202_final.repository.assignment.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AssignmentService {
    @Autowired
    private final AssignmentRepository repository;

    public Optional<ArrayList<Assignment>> findByCourse(String course){
        return repository.findByCourse(course);
    }

    public Assignment addItem(Assignment assignment){
        return repository.save(assignment);
    }
}
