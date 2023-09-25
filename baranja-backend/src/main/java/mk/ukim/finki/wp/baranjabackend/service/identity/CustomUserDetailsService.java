package mk.ukim.finki.wp.baranjabackend.service.identity;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.baranjabackend.model.Professor;
import mk.ukim.finki.wp.baranjabackend.model.Student;
import mk.ukim.finki.wp.baranjabackend.model.identity.CustomUserDetails;
import mk.ukim.finki.wp.baranjabackend.repository.ProfessorRepository;
import mk.ukim.finki.wp.baranjabackend.repository.StudentRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private static final String STUDENT_EMAIL_SUFFIX = "@students.finki.ukim.mk";
    private static final String STAFF_EMAIL_SUFFIX = "@finki.ukim.mk";


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email.endsWith(STUDENT_EMAIL_SUFFIX)){
            Optional<Student> student = studentRepository.findByEmail(email);
            if (student.isPresent()){
                User user = new User(student.get().getEmail(), student.get().getPassword(), student.get().getAuthorities());
                return new CustomUserDetails(user, student.get().getIndex(),student.get().getName() + " " + student.get().getLastName());
            }else{
                throw new IllegalArgumentException("Student not found");
            }
        }else if(email.endsWith(STAFF_EMAIL_SUFFIX)){
            Optional<Professor> professor = professorRepository.findByEmail(email);
            if (professor.isPresent()){
                User user = new User(professor.get().getEmail(), professor.get().getPassword(), professor.get().getAuthorities());
                return new CustomUserDetails(user, professor.get().getId(), professor.get().getName());
            }else{
                throw new IllegalArgumentException("Professor not found");
            }
        }else{
            throw new UsernameNotFoundException("Not recognized email");
        }
    }
}
