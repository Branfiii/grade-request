package mk.ukim.finki.wp.baranjabackend.controller;

import mk.ukim.finki.wp.baranjabackend.model.Professor;
import mk.ukim.finki.wp.baranjabackend.model.ProfessorTitle;
import mk.ukim.finki.wp.baranjabackend.service.ProfessorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public String getProfessors(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Professor> professors = this.professorService.findAll();
        model.addAttribute("professors", professors);
        model.addAttribute("bodyContent", "professors");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProfessor(@PathVariable String professorId) {
        this.professorService.deleteById(professorId);
        return "redirect:/professors";
    }

    @GetMapping("/edit/{id}")
    public String editProfessor(@PathVariable String id, Model model) {
        if (this.professorService.findProfessorById(id).isPresent()) {
            Professor professor = this.professorService.findProfessorById(id)
                    .orElseThrow(() -> new RuntimeException());
            model.addAttribute("professor", professor);
            model.addAttribute("bodyContent", "add-professor");
            return "master-template";
        }
        return "redirect:/professors?error=ProfessorNotFound";
    }

    @GetMapping("/add-form")
    public String addProfessor(Model model) {
        List<Professor> professors = this.professorService.findAll();
        model.addAttribute("professors", professors);
        model.addAttribute("bodyContent", "add-professor");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProfessor(@RequestParam String professorId,
                                @RequestParam String name,
                                @RequestParam String password,
                                @RequestParam String email,
                                @RequestParam ProfessorTitle title,
                                @RequestParam(required = false) Long professorEditId) {
        if(professorEditId == null){
            this.professorService.save(professorId,name, password,email,title)
                .orElseThrow(() -> new RuntimeException());
        }
//        else{
//            this.professorService.edit(professorId,firstName,email,title)
//                    .orElseThrow(() -> new RuntimeException(professorId));;
//        }

        return "redirect:/professors";
    }
}

