package com.grades.gradesmanagement.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50)
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50)
    private String lastName;
}