package com.example.productioReady.productioReady.entities;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}

// in Spring Security UserDetails is an interface that provides core user information. It is used throughout the framework as a user model and contains methods to get the username, password, and authorities, as well as account status flags (e.g., whether the account is locked or expired). By implementing this interface, you can integrate your user entity with Spring Security's authentication and authorization mechanisms.
// The UserEntity class represents a user in the system and implements the UserDetails interface from Spring Security. This allows instances of UserEntity to be used directly by Spring Security for authentication and authorization purposes.
// the methods overridden from UserDetails provide the necessary information about the user, such as their username (email), password, and granted authorities (roles/permissions). In this case, the getAuthorities method returns an empty list, indicating that the user has no specific roles or permissions assigned.
// we have few more methods in UserDetails like isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled which we have not implemented here. By default, these methods return true, indicating that the account is valid in all aspects. If you need to implement specific logic for these checks, you can override these methods in the UserEntity class.
