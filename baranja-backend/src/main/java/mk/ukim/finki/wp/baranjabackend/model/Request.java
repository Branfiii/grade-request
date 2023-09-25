package mk.ukim.finki.wp.baranjabackend.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Request {
    // requestRepo.findAll().stream().filter(req->!req.IsApproved()).ToList() -> kontrolerot so e na endpoint GetAllUnapprovedRequests
    // requestRepo.findAll().stream().filter(req->req.IsApproved()).ToList() -> kontrolerot so e na endpoint GetApprovedRequests

    @Id
    @GeneratedValue
    private int Id;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Subject subject;
    @ManyToOne
    private Professor professor;
    private Integer Approved;

    public Integer getApproved() {
        return Approved;
    }

    public void setApproved(Integer Approved) {
        this.Approved = Approved;
    }




    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }



    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }



    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    private LocalDate dateCreated;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    public Request(Student student, Subject subject, Professor professor, RequestType requestType) {
        this.student = student;
        this.subject = subject;
        this.professor = professor;
        this.Approved = 0;
        this.dateCreated = LocalDate.now();
        this.requestType = requestType;
    }
}
