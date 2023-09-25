package mk.ukim.finki.wp.baranjabackend.controller;

import mk.ukim.finki.wp.baranjabackend.model.SemesterType;
import mk.ukim.finki.wp.baranjabackend.model.Subject;
import mk.ukim.finki.wp.baranjabackend.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/subjects"})
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/")
    public List<Subject> findAll() {
        return subjectService.findAll();
    }

    @GetMapping("/{id}")
    public Subject findById(@PathVariable String id) {
        return subjectService.findById(id);
    }

    @PostMapping("/create")
    public Subject create(String id, String name, String abbreviation, SemesterType semester, Integer weeklyLecturesClasses, Integer weeklyAuditoriumClasses, Integer weeklyLabClasses) {
        Subject subject = new Subject(id, name, abbreviation, semester, weeklyLecturesClasses, weeklyAuditoriumClasses, weeklyLabClasses);
        return subjectService.create(subject);
    }

/*
    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable String id) {
        subjectService.delete(id);
    }

    @PutMapping("/{id}/edit")
    public Subject update(@PathVariable String id, @RequestBody SubjectDto subjectDto) {
        return subjectService.update(id, subjectDto);
    }*/
}
