package com.grades.gradesmanagement.service;
import com.grades.gradesmanagement.dto.GradeInputDTO;
import com.grades.gradesmanagement.dto.GradeOutputDTO;
import java.util.List;

public interface GradeService {
    GradeOutputDTO addGrade(GradeInputDTO gradeInputDTO);
    GradeOutputDTO updateGrade(Long gradeId, GradeInputDTO gradeInputDTO);
    void deleteGrade(Long gradeId);
    List<GradeOutputDTO> getGradesByStudentId(Long studentId);
    List<GradeOutputDTO> getGradesByCourseId(Long courseId);
}