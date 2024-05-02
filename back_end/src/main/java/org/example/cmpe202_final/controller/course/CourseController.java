package org.example.cmpe202_final.controller.course;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.controller.course.strategy.CourseStrategy;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.model.user.UserType;
import org.example.cmpe202_final.service.course.CourseService;
import org.example.cmpe202_final.service.semester.SemesterService;
import org.example.cmpe202_final.service.user.UserService;
import org.example.cmpe202_final.view.course.CourseViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    @Autowired
    private UserService userService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ArrayList<CourseViewEntity> getCourses(@RequestParam(required = false) String userId) {
        Optional<User> user = Optional.empty();
        if(userId != null){
            user = userService.findById(userId);
        }
        CourseStrategy strategy = UserType.UNKNOWN.getCourseStrategy();
        if(user.isPresent()){
            // Use user type strategy to present courses
            strategy = UserType.valueOf(
                    user.get().getType()
            ).getCourseStrategy();
        }
        return strategy.getCourseViews(
                courseService,
                semesterService,
                userService,
                userId
        );
    }
    @GetMapping("/{courseId}/students")
    public List<User> getStudentsByCourseId(@PathVariable String courseId) {
        return courseService.findStudentsByCourseId(courseId);
    }
}
