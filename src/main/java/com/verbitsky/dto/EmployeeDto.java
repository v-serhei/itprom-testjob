package com.verbitsky.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(exclude = {"profession", "department"}, callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto extends BaseDto {
    @Serial
    private static final long serialVersionUID = -1593787724545151112L;
    @NotNull(message = "Department firstName can't be empty")
    @NotEmpty(message = "Department firstName can't be empty")
    private String firstName;
    @NotNull(message = "Department lastName can't be empty")
    @NotEmpty(message = "Department lastName can't be empty")
    private String lastName;
    private String middleName;
    @NotNull(message = "Employee profession can't be null")
    private ProfessionDto profession;
    @NotNull(message = "Employee department can't be null")
    private DepartmentDto department;
}
