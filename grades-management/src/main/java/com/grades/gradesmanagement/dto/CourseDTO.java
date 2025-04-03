package com.grades.gradesmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    @NotBlank(message = "Course name cannot be blank")
    @Size(max = 100)
    private String name;
    @Size(max = 255)
    private String description;
}