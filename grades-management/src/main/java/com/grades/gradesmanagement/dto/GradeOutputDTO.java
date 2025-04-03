package com.grades.gradesmanagement.dto;
import lombok.Data;

@Data
public class GradeOutputDTO {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseName;
    private Double score;
}