package mk.ukim.finki.wp.baranjabackend.repository;

import mk.ukim.finki.wp.baranjabackend.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends PagingAndSortingRepository<Request, Integer>, JpaRepository<Request, Integer> {

    @Query("select c from Request c WHERE c.subject = ?1 AND c.requestType = ?3 AND c.student = ?2 AND c.professor =?4")
    Request findBySubjectAndStudentAndRequestTypeEquals(Subject subject, Student student,RequestType requestType, Professor professor);

    @Query("select c from Request c")
    Page<Request> findAllPage(Pageable pageable);

    @Query("select c from Request c WHERE c.student = ?1")
    Page<Request> findByStudent(Student student, Pageable pageable);

    @Query("select c from Request c WHERE c.Approved <> 2 AND c.professor = ?1")
    Page<Request> findByProfessor(Professor professor, Integer Approved, Pageable pageable);

    @Query("select c from Request c")
    Slice<Request> findAllSlice(Pageable pageable);

    @Query("select c from Request c")
    List<Request> findAllSorted(Sort sort);



    @Query(value = "SELECT * FROM requests as r " +
            "JOIN student_course ss ON r.course_id = ss.course_id " +
            "JOIN student s ON s.index = ss.student_index " +
            "WHERE s.index=:index", nativeQuery = true)
    List<Request> findByStudent(@Param("index") String index);
}
