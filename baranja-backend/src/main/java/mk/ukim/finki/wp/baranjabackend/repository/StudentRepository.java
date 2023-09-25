package mk.ukim.finki.wp.baranjabackend.repository;

import mk.ukim.finki.wp.baranjabackend.model.Professor;
import mk.ukim.finki.wp.baranjabackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, String > {
    Optional<Student> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByIndex(String index);
    Optional<Student> findByIndex(String studentIndex);

}
