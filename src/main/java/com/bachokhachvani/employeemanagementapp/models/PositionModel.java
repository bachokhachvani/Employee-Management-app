package com.bachokhachvani.employeemanagementapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "position")
public class PositionModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "POSITIONID")
        private Integer positionId;

        @Column(name = "NAME", length = 20, nullable = false)
        private String name;

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
