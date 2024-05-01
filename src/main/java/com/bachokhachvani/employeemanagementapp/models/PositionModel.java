package com.bachokhachvani.employeemanagementapp.models;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "position")
public class PositionModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "POSITION_ID")
        private Integer positionId;

        @Column(name = "NAME", length = 20, nullable = false)
        private String name;

}
