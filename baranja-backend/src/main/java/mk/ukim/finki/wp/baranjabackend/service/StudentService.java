package mk.ukim.finki.wp.baranjabackend.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.baranjabackend.model.*;
import mk.ukim.finki.wp.baranjabackend.repository.StudentRepository;
import mk.ukim.finki.wp.baranjabackend.repository.StudyProgramRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudyProgramRepository studyProgramRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Student> findStudentById(String id) throws RuntimeException {
        return Optional.ofNullable(studentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(id)));
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> createStudent(String firstName, String lastName, String email, String password, String index, String parentName, String studyProgramId) {
        Optional<StudyProgram> studyProgramOptional = studyProgramRepository.findByCode(studyProgramId);
//        Student student = new Student(firstName,lastName,email,passwordEncoder.encode(password),index,parentName, studyProgramOptional.orElse(null), UserRole.STUDENT)
        Student student = new Student(index, email, firstName, lastName, parentName, password, studyProgramOptional.orElse(null), UserRole.STUDENT);
        return Optional.of(this.studentRepository.save(student));
    }

 /*   public Optional<Student> editStudent(String name,  String lastName, String email, String password, String index, String parentName, String studyProgramId) throws RuntimeException {
        Optional<StudyProgram> studyProgramOptional = studyProgramRepository.findByCode(studyProgramId);
        Student student = this.studentRepository.findByIndex(index).orElseThrow(()-> new RuntimeException(index));
        student.setName(name == null ? student.getName() : name);
        student.setLastName(lastName == null ? student.getLastName() : lastName);
        student.setEmail(email == null ? student.getEmail() : email);
        student.setPassword(password == null ? student.getParentName() : passwordEncoder.encode(password));
        student.setParentName(parentName == null ? student.getParentName() : parentName);
        student.setStudyProgram(studyProgramOptional.orElse(null) ? student.getStudyProgram().getName() : studyProgramId);
        return Optional.of(this.studentRepository.save(student));
    }*/

//    public void deleteStudent(String index) throws RuntimeException {
//        Student student = findStudentById(index).orElseThrow(()-> new RuntimeException(index));
//        studentRepository.delete(student);
//    }
    public Optional<Student> save(String firstName, String lastName, String email, String password, String index, String parentName, String studyProgramId) {
        Optional<StudyProgram> studyProgramOptional = studyProgramRepository.findByCode(studyProgramId);
        Student student = new Student(index, email, firstName, lastName, parentName, passwordEncoder.encode(password), studyProgramOptional.orElse(null), UserRole.STUDENT);

        return Optional.of(this.studentRepository.save(student));
    }


}
