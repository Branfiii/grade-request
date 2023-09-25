package mk.ukim.finki.wp.baranjabackend.controller;

import mk.ukim.finki.wp.baranjabackend.model.Professor;
import mk.ukim.finki.wp.baranjabackend.model.Request;
import mk.ukim.finki.wp.baranjabackend.model.RequestType;
import mk.ukim.finki.wp.baranjabackend.model.Subject;
import mk.ukim.finki.wp.baranjabackend.model.identity.CustomUserDetails;
import mk.ukim.finki.wp.baranjabackend.model.identity.exception.DuplicateRequestException;
import mk.ukim.finki.wp.baranjabackend.repository.RequestRepository;
import mk.ukim.finki.wp.baranjabackend.service.ProfessorService;
import mk.ukim.finki.wp.baranjabackend.service.RequestService;
import mk.ukim.finki.wp.baranjabackend.service.SubjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Controller
//@RequestMapping("/requests")
public class RequestController {
    private final RequestService requestService;
    private final RequestRepository requestRepository;
    private final SubjectService subjectService;
    private final ProfessorService professorService;

    public RequestController(RequestService requestService, RequestRepository requestRepository, SubjectService subjectService, ProfessorService professorService) {
        this.requestService = requestService;
        this.requestRepository = requestRepository;
        this.subjectService = subjectService;
        this.professorService = professorService;
    }
    @GetMapping( "/")
    public String getHome(Model model) {

        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        return "home";
    }

    @GetMapping(value = {"/requests"})
    public String getRequests(@PageableDefault(page = 0, size = 5) Pageable pageable, Model model, Authentication authentication) {

        Page<Request> requests = requestService.findAllPage(pageable, authentication);
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer numPages = requests.getTotalPages();
        Integer currentPage = requests.getNumber();

        model.addAttribute("user", user);
        model.addAttribute("requests", requests);
        model.addAttribute("numPages", numPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("bodyContent", "requests");

        return "moiBaranja";
    }


    @GetMapping("/{type}")
    public String getRequestsByType(@RequestParam(required = false) String error, @PathVariable String type, Model model){
        if (error != null && !error.isEmpty()) {
            getModelWithErrors(model, error);
        }
        try{
            List<Request> requestsByType = this.requestService.getRequestsByType(type);
            model.addAttribute("requestsByType",requestsByType);
        } catch (Exception e) {
            getModelWithErrors(model,error);
        }
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "home";
    }

    private Model getModelWithErrors(Model model, String error){
        model.addAttribute("hasError", true);
        model.addAttribute("error", error);
        return model;
    }

    //create request
    @GetMapping("/request/add")
    public String addNewRequest(@RequestParam(required = false) String error ,Model model) {

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Subject> subjects = this.subjectService.findAll();
        List<Professor> professors = this.professorService.findAll();
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);

        model.addAttribute("subjects", subjects);
        model.addAttribute("professors", professors);
        model.addAttribute("requestTypes", RequestType.values());
        model.addAttribute("bodyContent", "add-request");
        return "baranjeZaPrijava";
    }

    @PostMapping("/request/add")
    public String addNewRequest(
            Authentication authentication,
            @RequestParam String subjectId,
            @RequestParam String professorId,
            @RequestParam RequestType requestType
    ) {
        try {
            this.requestService.create(authentication.getName(), subjectId, professorId, requestType.toString());
            return "redirect:/requests";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "redirect:/request/add?error="+ex.getMessage();
        }


    }



    @PostMapping("/request/{id}/approve")
    public String approveRequest(@PathVariable Integer id) {
        this.requestService.approve(id);

        return "redirect:/requests";
    }

    @PostMapping("/request/{id}/decline")
    public String declineRequest(@PathVariable Integer id) {
        this.requestService.decline(id);

        return "redirect:/requests";
    }


    //persmisii: studentot
}
