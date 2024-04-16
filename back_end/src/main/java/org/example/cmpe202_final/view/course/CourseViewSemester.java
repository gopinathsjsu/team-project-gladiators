package org.example.cmpe202_final.view.course;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cmpe202_final.model.course.Semester;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@AllArgsConstructor
@JsonTypeName(CourseViewEntity.SEMESTER_TYPE)
public class CourseViewSemester implements CourseViewEntity {

    @Id
    private String id;
    private Date startDate;
    private Date endDate;
    private String name;

    public CourseViewSemester(Semester semester) {
        this.id = semester.getId();
        this.startDate = semester.getStartDate();
        this.endDate = semester.getEndDate();
        this.name = semester.getName();
    }

    @Override
    public String getType() {
        return  CourseViewEntity.SEMESTER_TYPE;
    }
}
