package mk.ukim.finki.wp.baranjabackend.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import mk.ukim.finki.wp.baranjabackend.model.ProfessorTitle;
import mk.ukim.finki.wp.baranjabackend.model.SemesterType;
import mk.ukim.finki.wp.baranjabackend.model.Subject;
import mk.ukim.finki.wp.baranjabackend.service.ProfessorService;
import mk.ukim.finki.wp.baranjabackend.service.StudentService;
import mk.ukim.finki.wp.baranjabackend.service.SubjectService;
import org.springframework.stereotype.Component;

@Component
@Getter
public class DataInit {
    private final ProfessorService professorService;
    private final StudentService studentService;
    private final SubjectService subjectService;

    public DataInit(ProfessorService professorService, StudentService studentService, SubjectService subjectService) {
        this.professorService = professorService;
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

   @PostConstruct
    public void init() {

            professorService.save("ivan.chorbev", "Ivan Chorbev", "pass123", "ivan@finki.ukim.mk", ProfessorTitle.PROFESSOR);
            professorService.save("sasho.gramatikov", "Sasho Gramatikov", "pass123", "sasho@finki.ukim.mk", ProfessorTitle.PROFESSOR);
            professorService.save("riste.stojanov", "Riste Stojanov", "pass123", "riste@finki.ukim.mk", ProfessorTitle.PROFESSOR);

            studentService.save("Branislav", "Markovikj", "branislav.markovikj@students.finki.ukim.mk", "pass123", "183284", "Granjoslav", "PIT");
            studentService.save("Dalibor", "Petrovikj", "dalibor.petrovikj@students.finki.ukim.mk", "pass123", "183269", "Milan", "PIT");

           Subject subject1 = new Subject("F18L1W132", "Operativni Sistemi", "OS", SemesterType.SUMMER, 10, 10, 30);
           Subject subject2 = new Subject("F18L3W322", "Veb Dizajn", "VD", SemesterType.WINTER, 10, 10, 30);
           Subject subject3 = new Subject("F18L2W111", "Diskretna Matematika", "DS", SemesterType.WINTER, 10, 10, 30);

           subjectService.create(subject1);
           subjectService.create(subject2);
           subjectService.create(subject3);

    }
}
