package com.verbitsky.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "PROFESSION")
public class ProfessionEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
}
