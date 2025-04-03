package com.grades.gradesmanagement.service;
import com.grades.gradesmanagement.dto.AverageGradeDTO;

public interface ReportService {
    AverageGradeDTO getAverageGradeForCourse(Long courseId);
    AverageGradeDTO getAverageGradeForStudent(Long studentId);
}