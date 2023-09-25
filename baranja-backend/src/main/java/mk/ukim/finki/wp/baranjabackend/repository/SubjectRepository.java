package mk.ukim.finki.wp.baranjabackend.repository;

import mk.ukim.finki.wp.baranjabackend.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    List<Subject> findByName(String name);
}
