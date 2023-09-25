package mk.ukim.finki.wp.baranjabackend.service;

//import mk.ukim.finki.wp.baranjabackend.model.*;
import mk.ukim.finki.wp.baranjabackend.model.*;
import mk.ukim.finki.wp.baranjabackend.model.identity.CustomUserDetails;
import mk.ukim.finki.wp.baranjabackend.model.identity.exception.DuplicateRequestException;
import mk.ukim.finki.wp.baranjabackend.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    public RequestService(RequestRepository requestRepository, StudentRepository studentRepository, SubjectRepository subjectRepository, CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.requestRepository = requestRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    public Page<Request> findAllPage(Pageable pageable, Authentication auth) {
        Page<Request> result = null;

        if(auth.getAuthorities().toString().equals("[STUDENT]")) {
            CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Student student = studentRepository.findById(user.getId()).orElseThrow(RuntimeException::new);
            result = requestRepository.findByStudent(student, pageable);
        } else if (auth.getAuthorities().toString().equals("[PROFESSOR]")) {
            CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Professor professor = professorRepository.findById(user.getId()).orElseThrow(RuntimeException::new);
            result = requestRepository.findByProfessor(professor,2, pageable);
        }


        return result;
    }

    public Request findById(Integer id) {
        return requestRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(String.format("Request with id %s does not exist", id)));
    }

    public List<Request> findByStudent(String studentIndex) {
        Student student = studentRepository.findByIndex(studentIndex)
                .orElseThrow(() ->
                        new RuntimeException(String.format("Student with index %s does not exist", studentIndex)));

        return requestRepository.findByStudent(student.getIndex().toString());
    }

    public Request create(String studentEmail, String subjectId, String professorId, String requestType) {
        Student student = studentRepository.findByEmail(studentEmail).orElseThrow(RuntimeException::new);
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() ->
                new RuntimeException(String.format("Subject with id %s does not exist", subjectId)));
        Professor professor = professorRepository.findById(professorId).orElseThrow(() ->
                new RuntimeException(String.format("Professor with id %s does not exist", professorId)));
        Request request = new Request(student, subject, professor, RequestType.valueOf(requestType));

        Request r = requestRepository.findBySubjectAndStudentAndRequestTypeEquals(subject, student,RequestType.valueOf(requestType), professor);

        System.out.println(r);
        if(r != null)
            throw new DuplicateRequestException(request.getRequestType().toString(),request.getProfessor().getName(), request.getSubject().getName());
        else return requestRepository.save(request);
//return null;
    }

    public Request approve(Integer id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(String.format("Request with id %s does not exist", id)));

        request.setApproved(1);

        return requestRepository.save(request);
    }

    public Request decline( Integer id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(String.format("Request with id %s does not exist", id)));

        request.setApproved(2);

        return requestRepository.save(request);
    }

    public List<Request> getRequestsByType(String type) throws Exception {
        if (type == null){
            throw new Exception("Type is null.");
        }

        try{
            RequestType requestType = RequestType.valueOf(type);

            return requestRepository.findAll().stream().filter(request -> request.getRequestType().equals(requestType
            )).collect(Collectors.toList());
        }catch (Exception e){
            throw e;
        }
    }
}
