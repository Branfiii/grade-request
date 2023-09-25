package mk.ukim.finki.wp.baranjabackend.repository;

import mk.ukim.finki.wp.baranjabackend.model.Course;
import mk.ukim.finki.wp.baranjabackend.model.Professor;
import mk.ukim.finki.wp.baranjabackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
//    Optional<Professor> findByFirstName(String name);
    Optional<Professor> findByEmail(String email);

    Optional<Professor> findById(String id);

}
