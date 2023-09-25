package mk.ukim.finki.wp.baranjabackend.service;

import mk.ukim.finki.wp.baranjabackend.model.Semester;
import mk.ukim.finki.wp.baranjabackend.model.Subject;
import mk.ukim.finki.wp.baranjabackend.repository.SemesterRepository;
import mk.ukim.finki.wp.baranjabackend.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;

    public SubjectService(SubjectRepository subjectRepository, SemesterRepository semesterRepository) {
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
    }

    public Subject findById(String id) {
        return subjectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(String.format("Subject with id %s does not exist", id)));
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public List<Subject> findByName(String name) {
        return subjectRepository.findByName(name);
    }

    public Subject create(Subject subject) {
//        if(subjectRepository.existsById(subject.getId()))
//            throw new RuntimeException(String.format("Subject with id %s already exists", subject.getId()));

//        Subject subject1 = ;
//        Semester semester = semesterRepository.findById(subject.getSemester())
        return subjectRepository.save(new Subject(subject.getId(), subject.getName(), subject.getAbbreviation(), subject.getSemester(), subject.getWeeklyLecturesClasses(), subject.getWeeklyAuditoriumClasses(), subject.getWeeklyLabClasses()));

    }
/*
    public Subject update(String id, SubjectDto subjectDto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(String.format("Subject with %s does not exist", id)));

        subject.setId(subjectDto.getId());
        subject.setName(subject.getName());
        subject.setSemester(subjectDto.getSemester());

        return subjectRepository.save(subject);
    }

    public void delete(String id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(String.format("Subject with %s does not exist", id)));

        subjectRepository.delete(subject);
    }*/
}
