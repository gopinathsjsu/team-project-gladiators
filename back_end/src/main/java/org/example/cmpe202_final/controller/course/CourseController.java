package org.example.cmpe202_final.controller.course;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.controller.course.strategy.CourseStrategy;
import org.example.cmpe202_final.model.announcement.Announcement;
import org.example.cmpe202_final.model.assignment.Assignment;
import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.model.course.CourseInput;
import org.example.cmpe202_final.model.course.Semester;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.model.user.UserType;
import org.example.cmpe202_final.service.announcement.AnnouncementService;
import org.example.cmpe202_final.service.course.CourseService;
import org.example.cmpe202_final.service.semester.SemesterService;
import org.example.cmpe202_final.service.user.UserService;
import org.example.cmpe202_final.view.course.CourseDetails;
import org.example.cmpe202_final.view.course.CourseViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private AnnouncementService announcementService;

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

    @GetMapping("/{courseId}/details")
    public CourseDetails getCourseDetails(@PathVariable String courseId) {
        ArrayList<Announcement> announcements = announcementService.getAnnouncementsByCourse(courseId);
        Optional<Course> course = courseService.findById(courseId);
        if(course.isEmpty()){
            return new CourseDetails(null, null, announcements);
        }
        if(course.get().getInstructor() == null){
            return new CourseDetails(course.get(), null, announcements);
        }
        Optional<User> instructor = userService.findById(course.get().getInstructor());
        if(instructor.isEmpty()){
            return new CourseDetails(course.get(), null, announcements);
        }
        return new CourseDetails(course.get(), instructor.get(), announcements);
    }

    @GetMapping("/input")
    public CourseInput getCourseInputData(){
        List<Semester> semesters = semesterService.findAllSemesters();
        List<User> instructors = userService.findByType(UserType.FACULTY);
        return new CourseInput(
                new ArrayList<>(semesters),
                new ArrayList<>(instructors)
        );
    }

    @PostMapping
    public ArrayList<CourseViewEntity> createCourse(@RequestBody Course course){
        courseService.addItem(course);
        return UserType.ADMIN.getCourseStrategy().getCourseViews(
                courseService,
                semesterService,
                userService,
                "" // All admins can see the same content
        );
    }

    @PostMapping("/update")
    public CourseDetails updateCourse(@RequestBody Course course) {
        ArrayList<Announcement> announcements = announcementService.getAnnouncementsByCourse(course.getId());
        Course updatedCourse = courseService.addItem(course);
        if (updatedCourse.getInstructor() == null) {
            return new CourseDetails(updatedCourse, null, announcements);
        }
        Optional<User> instructor = userService.findById(updatedCourse.getInstructor());
        if (instructor.isEmpty()) {
            return new CourseDetails(updatedCourse, null, announcements);
        }

        return new CourseDetails(updatedCourse, instructor.get(), announcements);
    }

    @PostMapping("/announcement")
    public CourseDetails postAnnouncement(@RequestBody Announcement announcement) {
        announcementService.saveAnnouncement(announcement);
        ArrayList<Announcement> announcements = announcementService.getAnnouncementsByCourse(announcement.getCourseId());
        Course updatedCourse = courseService.findCourseById(announcement.getCourseId());
        if (updatedCourse.getInstructor() == null) {
            return new CourseDetails(updatedCourse, null, announcements);
        }
        Optional<User> instructor = userService.findById(updatedCourse.getInstructor());
        if (instructor.isEmpty()) {
            return new CourseDetails(updatedCourse, null, announcements);
        }

        return new CourseDetails(updatedCourse, instructor.get(), announcements);
    }
}
