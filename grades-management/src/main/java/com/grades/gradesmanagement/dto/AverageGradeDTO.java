package com.grades.gradesmanagement.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AverageGradeDTO {
    private String identifier;
    private Double averageScore;
    private int numberOfGrades;
}