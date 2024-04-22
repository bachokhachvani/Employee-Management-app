package com.bachokhachvani.employeemanagementapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "token")
public class TokenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TOKEN", nullable = false)
    private String token;

    @Column(name = "IS_LOGGED_OUT", nullable = false)
    private boolean loggedOut;

    @ManyToOne
    @JoinColumn(name = "USERID")
    @JsonBackReference
    private UserModel user;
}
