package org.example.cmpe202_final.view.course;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.model.user.UserType;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
@JsonTypeName(CourseViewEntity.FACULTY_TYPE)
public class CourseViewFaculty implements  CourseViewEntity{
    @Id
    private String id;
    private String password;
    private String type;

    private String firstName;

    private String lastName;


    public CourseViewFaculty(User user) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.type = user.getType();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    @Override
    public String getType() {
        return  CourseViewEntity.FACULTY_TYPE;
    }

    public static CourseViewFaculty getUnassignedView(){
        return new CourseViewFaculty(
                "UNASSIGNED",
                "UNASSIGNED",
                UserType.UNKNOWN.name(),
                "Unassigned",
                ""
        );
    }
}
