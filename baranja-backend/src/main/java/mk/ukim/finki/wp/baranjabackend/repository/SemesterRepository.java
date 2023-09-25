package mk.ukim.finki.wp.baranjabackend.repository;

import mk.ukim.finki.wp.baranjabackend.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, String> {
}
