package com.bachokhachvani.employeemanagementapp.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "`user`")
public class UserModel implements UserDetails {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "USERNAME", length = 20, nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", length = 60, nullable = false)
    private String password;

    @Getter
    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private RoleModel role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role.getName());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
}
