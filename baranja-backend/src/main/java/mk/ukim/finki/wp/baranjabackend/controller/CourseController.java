package mk.ukim.finki.wp.baranjabackend.controller;

import mk.ukim.finki.wp.baranjabackend.model.Course;
import mk.ukim.finki.wp.baranjabackend.model.Semester;
import mk.ukim.finki.wp.baranjabackend.model.Subject;
import mk.ukim.finki.wp.baranjabackend.repository.SemesterRepository;
import mk.ukim.finki.wp.baranjabackend.repository.SubjectRepository;
import mk.ukim.finki.wp.baranjabackend.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseController {


    private final CourseService courseService;
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;

    public CourseController(CourseService courseService, SubjectRepository subjectRepository, SemesterRepository semesterRepository) {
        this.courseService = courseService;
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
    }

    @GetMapping
    public String getcoursePage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Course> courses = this.courseService.findAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("bodyContent", "courses");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deletecourse(@PathVariable String id) {
        this.courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    @GetMapping("/edit-form/{id}")
    public String editcoursePage(@PathVariable String id, Model model) {
        if (this.courseService.findCourseById(id).isPresent()) {
            Course course = this.courseService.findCourseById(id).get();
            List<Subject> subjects = this.subjectRepository.findAll();
            List<Semester> semesters = this.semesterRepository.findAll();
            model.addAttribute("subjects", subjects);
            model.addAttribute("semesters", semesters);
            model.addAttribute("course", course);
            model.addAttribute("bodyContent", "add-course");
            return "master-template";
        }
        return "redirect:/courses?error=courseNotFound";
    }

    @GetMapping("/add-form")
    public String addcoursePage(Model model) {
        List<Subject> subjects = this.subjectRepository.findAll();
        List<Semester> semesters = this.semesterRepository.findAll();
        model.addAttribute("subjects", subjects);
        model.addAttribute("semesters", semesters);
        model.addAttribute("bodyContent", "add-course");
        return "master-template";
    }

    @PostMapping("/add")
    public String savecourse(
            @RequestParam String id,
            @RequestParam String semesterId,
            @RequestParam String subjectId1,
            @RequestParam String subjectId2,
            @RequestParam String subjectId3,
            @RequestParam Long totalStudents,
            @RequestParam Long totalTeachingStaff,
            @RequestParam Boolean adding) {
        if (adding) {
            this.courseService.createCourse(id, semesterId, subjectId1, subjectId2, subjectId3, totalStudents, totalTeachingStaff);
        } else {
            this.courseService.editCourse(id, semesterId, subjectId1, subjectId2, subjectId3, totalStudents, totalTeachingStaff);

        }
        return "redirect:/courses";
    }

}
