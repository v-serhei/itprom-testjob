package com.verbitsky.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;

    @ManyToOne
    @JoinColumn(name = "profession_id")
    private ProfessionEntity profession;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
}
