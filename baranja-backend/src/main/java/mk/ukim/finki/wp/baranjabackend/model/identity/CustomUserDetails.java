package mk.ukim.finki.wp.baranjabackend.model.identity;

import lombok.Builder;
import mk.ukim.finki.wp.baranjabackend.model.Student;
import mk.ukim.finki.wp.baranjabackend.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private User user;

    private String id;
    private String name;

    public CustomUserDetails(final User _user, String id, String name) {
        this.user = _user;
        this.id = id;
        this.name = name;
    }

    private CustomUserDetails() {
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (this.user == null) {
            return null;
        }
        return this.user.getUsername();
    }

    public String getFullName() {
        if(this.user == null) {
            return null;
        }
        return this.name;
    }

    public String getId() {
        if(this.user == null) {
            return null;
        }
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }
}