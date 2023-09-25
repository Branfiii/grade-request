package mk.ukim.finki.wp.baranjabackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class User {


    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    protected String firstName;

    protected String lastName;

    @Column(unique=true)
    protected String email;

    protected String password;

    @Enumerated(EnumType.STRING)
    protected UserRole role;


    public User(UserRole userRole) {
        this.role = userRole;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }*/

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
