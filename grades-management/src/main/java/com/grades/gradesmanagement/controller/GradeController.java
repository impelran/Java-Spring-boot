package com.grades.gradesmanagement.controller;
import com.grades.gradesmanagement.dto.GradeInputDTO;
import com.grades.gradesmanagement.dto.GradeOutputDTO;
import com.grades.gradesmanagement.service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<GradeOutputDTO> addGrade(@Valid @RequestBody GradeInputDTO gradeInputDTO) {
        GradeOutputDTO savedGrade = gradeService.addGrade(gradeInputDTO);
        return new ResponseEntity<>(savedGrade, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeOutputDTO> updateGrade(@PathVariable Long id, @Valid @RequestBody GradeInputDTO gradeInputDTO) {
        GradeOutputDTO updatedGrade = gradeService.updateGrade(id, gradeInputDTO);
        return ResponseEntity.ok(updatedGrade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeOutputDTO>> getGradesByStudentId(@PathVariable Long studentId) {
        List<GradeOutputDTO> grades = gradeService.getGradesByStudentId(studentId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<GradeOutputDTO>> getGradesByCourseId(@PathVariable Long courseId) {
        List<GradeOutputDTO> grades = gradeService.getGradesByCourseId(courseId);
        return ResponseEntity.ok(grades);
    }
}