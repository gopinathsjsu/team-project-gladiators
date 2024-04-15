package org.example.cmpe202_final.service.semester;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.course.Semester;
import org.example.cmpe202_final.repository.semester.SemesterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SemesterService {
    private final SemesterRepository repository;

    public List<Semester> findAllSemesters(){
        return repository.findAll();
    }
}
