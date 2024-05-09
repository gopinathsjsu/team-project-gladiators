package org.example.cmpe202_final.controller.assignment;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.assignment.Assignment;
import org.example.cmpe202_final.service.assignment.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/assignments")
@AllArgsConstructor
public class AssignmentController {

    @Autowired
    private final AssignmentService assignmentService;

    @GetMapping("/{course}")
    public ArrayList<Assignment> getAssignments(@PathVariable String course) {
        Optional<ArrayList<Assignment>> assignments = assignmentService.findByCourse(course);
        if(assignments.isPresent()){
            return assignments.get();
        }else {
            return new ArrayList<>();
        }
    }

    @PostMapping
    public ArrayList<Assignment> addAssignment(@RequestBody Assignment assignment){
        assignmentService.addItem(assignment);
        Optional<ArrayList<Assignment>> assignments = assignmentService.findByCourse(assignment.getCourse());
        if(assignments.isPresent()){
            return assignments.get();
        }else {
            return new ArrayList<>();
        }
    }
}
