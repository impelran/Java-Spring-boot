package com.grades.gradesmanagement.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradeInputDTO {
    @NotNull(message = "Student ID cannot be null")
    private Long studentId;
    @NotNull(message = "Course ID cannot be null")
    private Long courseId;
    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 100, message = "Score must be at most 100")
    private Double score;
}