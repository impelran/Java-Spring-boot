package com.grades.gradesmanagement.controller;
import com.grades.gradesmanagement.dto.AverageGradeDTO;
import com.grades.gradesmanagement.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<AverageGradeDTO> getCourseAverageReport(@PathVariable Long courseId) {
        AverageGradeDTO report = reportService.getAverageGradeForCourse(courseId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<AverageGradeDTO> getStudentAverageReport(@PathVariable Long studentId) {
        AverageGradeDTO report = reportService.getAverageGradeForStudent(studentId);
        return ResponseEntity.ok(report);
    }
}