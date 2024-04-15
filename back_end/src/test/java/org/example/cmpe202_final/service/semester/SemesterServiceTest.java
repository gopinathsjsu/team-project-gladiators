package org.example.cmpe202_final.service.semester;

import org.example.cmpe202_final.model.course.Semester;
import org.example.cmpe202_final.repository.semester.SemesterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SemesterServiceTest {

    @Mock
    private SemesterRepository repository;

    @InjectMocks
    private SemesterService semesterService;

    @Test
    public void testFindAllSemesters() {
        // Mock repository behavior
        Semester semester1 = new Semester("1", null, null, "Semester 1");
        Semester semester2 = new Semester("2", null, null, "Semester 2");
        List<Semester> semesters = Arrays.asList(semester1, semester2);
        when(repository.findAll()).thenReturn(semesters);

        // Test service method
        List<Semester> result = semesterService.findAllSemesters();
        assertEquals(semesters.size(), result.size());
        assertEquals(semesters, result);
    }
}
