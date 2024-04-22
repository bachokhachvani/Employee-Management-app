package com.bachokhachvani.employeemanagementapp.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Setter
@Getter
@Entity
@Table(name = "employee")
public class EmployeeModel {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PHONE", nullable = false)
    private Integer phone;

    @Column(name = "BIRTHDAY", nullable = false)
    private Date birthday;

    @Column(name = "NAME", length = 30, nullable = false)
    private String name;

    @Column(name = "EMAIL", length = 30, nullable = false, unique = true)
    private String email;

    @Column(name = "HIREDATE", nullable = false)
    private Date hireDate;

    @Column(name = "SALARY", nullable = false)
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT", nullable = false)
    private DepartmentModel department;

    @ManyToOne
    @JoinColumn(name = "POSITIONID", nullable = false)
    private PositionModel position;

    @ManyToOne
    @JoinColumn(name = "MANAGER")
    private EmployeeModel manager;


}
