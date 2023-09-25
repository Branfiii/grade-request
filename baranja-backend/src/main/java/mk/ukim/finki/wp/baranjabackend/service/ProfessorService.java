package mk.ukim.finki.wp.baranjabackend.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.baranjabackend.model.Professor;
import mk.ukim.finki.wp.baranjabackend.model.ProfessorTitle;
import mk.ukim.finki.wp.baranjabackend.model.Student;
import mk.ukim.finki.wp.baranjabackend.model.UserRole;
import mk.ukim.finki.wp.baranjabackend.repository.ProfessorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Professor> findProfessorById(String professorId) throws RuntimeException {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException(professorId));
        return Optional.of(professor);
    }

    public List<Professor> findAll() {
        return this.professorRepository.findAll();
    }

    public Optional<Professor> save(String id, String name, String password, String email, ProfessorTitle title) {

        Professor professor = new Professor(id, name,email,passwordEncoder.encode(password),title, UserRole.PROFESSOR);
        this.professorRepository.save(professor);
        return Optional.of(professor);
    }

    public Optional<Professor> edit(String professorId, String name, String email, ProfessorTitle title) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException(professorId));;
        professor.setName(name);
        professor.setEmail(email);
        professor.setTitle(title);
        this.professorRepository.save(professor);
        return Optional.of(professor);
    }

    public void deleteById(String professorId) {
        this.professorRepository.deleteById(professorId);
    }

}
