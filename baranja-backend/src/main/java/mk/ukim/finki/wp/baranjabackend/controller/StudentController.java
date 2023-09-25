package mk.ukim.finki.wp.baranjabackend.controller;

import mk.ukim.finki.wp.baranjabackend.model.Student;
import mk.ukim.finki.wp.baranjabackend.model.StudyProgram;
import mk.ukim.finki.wp.baranjabackend.repository.StudyProgramRepository;
import mk.ukim.finki.wp.baranjabackend.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")

public class StudentController {
    private final StudentService studentService;
    private final StudyProgramRepository studyProgramRepository;

    public StudentController(StudentService studentService, StudyProgramRepository studyProgramRepository) {
        this.studentService = studentService;
        this.studyProgramRepository = studyProgramRepository;
    }

    @GetMapping
    public String getStudentPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Student> students = this.studentService.findAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("bodyContent", "students");
        return "master-template";
    }

    @DeleteMapping("/delete/{index}")
    public String deleteStudent(@PathVariable String index) {
//        this.studentService.deleteStudent(index);
        return "redirect:/students";
    }

    @GetMapping("/edit-form/{index}")
    public String editStudentPage(@PathVariable String index, Model model) {
        if (this.studentService.findStudentById(index).isPresent()) {
            Student student = this.studentService.findStudentById(index).get();
            List<StudyProgram> studyPrograms = this.studyProgramRepository.findAll();;
            model.addAttribute("studyPrograms", studyPrograms);
            model.addAttribute("student", student);
            model.addAttribute("bodyContent", "add-student");
            return "master-template";
        }
        return "redirect:/students?error=StudentNotFound";
    }

    @GetMapping("/add-form")
    public String addStudentPage(Model model) {
        List<StudyProgram> studyPrograms = this.studyProgramRepository.findAll();;
        model.addAttribute("studyPrograms", studyPrograms);
        model.addAttribute("bodyContent", "add-student");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveStudent(
         //  @RequestParam String studentId,
            @RequestParam String index,
            @RequestParam String email,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String parentName,
            @RequestParam String studyProgramId,
            @RequestParam String password,
            @RequestParam Boolean adding) {
        if (adding) {
//            this.studentService.createStudent(firstName, lastName, email, password, index, parentName, studyProgramId);
        } else {
           // this.studentService.editStudent( firstName, lastName, email, password, index, parentName, studyProgramId);
        }
        return "redirect:/students";
    }

}
