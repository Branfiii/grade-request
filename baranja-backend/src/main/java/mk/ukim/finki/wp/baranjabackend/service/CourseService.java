package mk.ukim.finki.wp.baranjabackend.service;

import mk.ukim.finki.wp.baranjabackend.model.Course;
import mk.ukim.finki.wp.baranjabackend.model.Semester;
import mk.ukim.finki.wp.baranjabackend.model.Subject;
import mk.ukim.finki.wp.baranjabackend.repository.CourseRepository;
import mk.ukim.finki.wp.baranjabackend.repository.SemesterRepository;
import mk.ukim.finki.wp.baranjabackend.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;

    public CourseService(CourseRepository courseRepository, SubjectRepository subjectRepository, SemesterRepository semesterRepository) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
    }


    public Optional<Course> findCourseById(String id) throws RuntimeException {
        return Optional.ofNullable(courseRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }


    public void deleteCourse(String id) throws RuntimeException {
        Course course = findCourseById(id).orElseThrow(RuntimeException::new);
        courseRepository.delete(course);
    }

    public Optional<Course> createCourse(String id, String semesterId, String subjectId1, String subjectId2, String subjectId3, Long totalStudents, Long totalTeachingStaff) {
        Course course = new Course();
        course.setId(id);
        Semester semester = semesterRepository.findById(semesterId).orElseThrow(RuntimeException::new);
        course.setSemester(semester);
        Subject subject1=subjectRepository.findById(subjectId1).orElseThrow(RuntimeException::new);
        course.setSubject(subject1);
        Subject subject2=subjectRepository.findById(subjectId2).orElseThrow(RuntimeException::new);
        course.setSubject2(subject2);
        Subject subject3=subjectRepository.findById(subjectId3).orElseThrow(RuntimeException::new);
        course.setSubject3(subject3);
        course.setTotalStudents(totalStudents);
        course.setTotalTeachingStaff(totalTeachingStaff);

        return Optional.of(courseRepository.save(course));
    }

    public Optional<Course> editCourse(String id, String semesterId, String subjectId1, String subjectId2, String subjectId3, Long totalStudents, Long totalTeachingStaff) throws RuntimeException {
        Course course = findCourseById(id).orElseThrow(RuntimeException::new);
        Semester semester = semesterRepository.findById(semesterId).orElseThrow(RuntimeException::new);
        course.setSemester(semester);
        Subject subject1=subjectRepository.findById(subjectId1).orElseThrow(RuntimeException::new);
        course.setSubject(subject1);
        Subject subject2=subjectRepository.findById(subjectId2).orElseThrow(RuntimeException::new);
        course.setSubject2(subject2);
        Subject subject3=subjectRepository.findById(subjectId3).orElseThrow(RuntimeException::new);
        course.setSubject3(subject3);
        course.setTotalStudents(totalStudents);
        course.setTotalTeachingStaff(totalTeachingStaff);

        return Optional.of(courseRepository.save(course));
    }



}
