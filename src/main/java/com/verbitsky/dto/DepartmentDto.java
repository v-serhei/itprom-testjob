package com.verbitsky.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentDto extends BaseDto {
    @Serial
    private static final long serialVersionUID = 5194668559736171931L;
    @NotNull(message = "Department name can't be null")
    @NotEmpty(message = "Department name can't be empty")
    private String name;
    private Long parentDepartmentId;
}
