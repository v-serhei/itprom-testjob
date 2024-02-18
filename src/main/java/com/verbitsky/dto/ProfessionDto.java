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
public class ProfessionDto extends BaseDto {
    @Serial
    private static final long serialVersionUID = -9196968238984941270L;
    @NotNull(message = "Profession name can't be null")
    @NotEmpty(message = "Profession name can't be null")
    private String name;
}
