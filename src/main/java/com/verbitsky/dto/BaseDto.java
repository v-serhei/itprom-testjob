package com.verbitsky.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BaseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -3521040554003434525L;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("note")
    private String note;
}
