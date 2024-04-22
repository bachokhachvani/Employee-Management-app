package com.bachokhachvani.employeemanagementapp.models;
import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "employee")
public class EmployeeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PHONE", nullable = false)
    private Integer phone;

    @Column(name = "BIRTHDAY", nullable = false)
    private Date birthday;

    @Column(name = "EMAIL", length = 30, nullable = false)
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }

    public PositionModel getPosition() {
        return position;
    }

    public void setPosition(PositionModel position) {
        this.position = position;
    }

    public EmployeeModel getManager() {
        return manager;
    }

    public void setManager(EmployeeModel manager) {
        this.manager = manager;
    }

    // For bi-directional relationship (if needed)
    // @OneToMany(mappedBy = "manager")
    // private Set<Employee> subordinates;
}
