package mk.ukim.finki.wp.baranjabackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student  implements UserDetails {

    @Id
    @Column(name = "student_index")
    private String index;

    private String email;

    private String name;

    private String lastName;

    private String parentName;

    public Student(String index, String email, String name, String lastName, String parentName, String password, StudyProgram studyProgram, UserRole role) {
        this.index = index;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.parentName = parentName;
        this.password = password;
        this.studyProgram = studyProgram;
        this.role = role;
    }

    private String password;

    @ManyToOne
    private StudyProgram studyProgram;
    protected UserRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;
        return getIndex() != null && Objects.equals(getIndex(), student.getIndex());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
