package com.bachokhachvani.employeemanagementapp.models;

import com.bachokhachvani.employeemanagementapp.domain.Role;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME", length = 20)
    private Role name;

}
