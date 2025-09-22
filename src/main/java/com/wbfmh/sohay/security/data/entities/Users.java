package com.wbfmh.sohay.security.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    private Long id;
    private String username;
    private String password;
    private String roles; // e.g., "ROLE_USER,ROLE_ADMIN"
    private LocalDate expiryDate;
    private Integer passwordAttempt = 0;
    private boolean enabled = true;
    private boolean expired = false;
    private boolean accountLocked = false;
    private boolean credentialLocked = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(roles.split(",")).map(
                SimpleGrantedAuthority::new
        ).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialLocked;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
