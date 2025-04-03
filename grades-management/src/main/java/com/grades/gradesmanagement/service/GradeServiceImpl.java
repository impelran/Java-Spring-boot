package com.grades.gradesmanagement.service;
import com.grades.gradesmanagement.dto.GradeInputDTO;
import com.grades.gradesmanagement.dto.GradeOutputDTO;
import com.grades.gradesmanagement.exception.ResourceNotFoundException;
import com.grades.gradesmanagement.model.Course;
import com.grades.gradesmanagement.model.Grade;
import com.grades.gradesmanagement.model.Student;
import com.grades.gradesmanagement.repository.CourseRepository;
import com.grades.gradesmanagement.repository.GradeRepository;
import com.grades.gradesmanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private GradeOutputDTO toOutputDto(Grade grade) {
        GradeOutputDTO dto = new GradeOutputDTO();
        dto.setId(grade.getId());
        dto.setScore(grade.getScore());
        if (grade.getStudent() != null) {
            dto.setStudentId(grade.getStudent().getId());
            dto.setStudentName(grade.getStudent().getFirstName() + " " + grade.getStudent().getLastName());
        }
        if (grade.getCourse() != null) {
            dto.setCourseId(grade.getCourse().getId());
            dto.setCourseName(grade.getCourse().getName());
        }
        return dto;
    }

    @Override
    @Transactional
    public GradeOutputDTO addGrade(GradeInputDTO gradeInputDTO) {
        Student student = studentRepository.findById(gradeInputDTO.getStudentId())
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + gradeInputDTO.getStudentId()));

        Course course = courseRepository.findById(gradeInputDTO.getCourseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + gradeInputDTO.getCourseId()));

         Optional<Grade> existingGrade = gradeRepository.findByStudentIdAndCourseId(student.getId(), course.getId());
         if (existingGrade.isPresent()) {
              throw new IllegalArgumentException("Grade already exists for student " + student.getId() + " in course " + course.getId());
         }


        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setScore(gradeInputDTO.getScore());

        Grade savedGrade = gradeRepository.save(grade);
        return toOutputDto(savedGrade);
    }

    @Override
    @Transactional
    public GradeOutputDTO updateGrade(Long gradeId, GradeInputDTO gradeInputDTO) {
        Grade existingGrade = gradeRepository.findById(gradeId)
            .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + gradeId));

        if (!existingGrade.getStudent().getId().equals(gradeInputDTO.getStudentId()) ||
            !existingGrade.getCourse().getId().equals(gradeInputDTO.getCourseId())) {
             throw new IllegalArgumentException("StudentId or CourseId in request does not match the grade being updated.");
         }


        existingGrade.setScore(gradeInputDTO.getScore());
        Grade updatedGrade = gradeRepository.save(existingGrade);
        return toOutputDto(updatedGrade);
    }

    @Override
    @Transactional
    public void deleteGrade(Long gradeId) {
        if (!gradeRepository.existsById(gradeId)) {
            throw new ResourceNotFoundException("Grade not found with id: " + gradeId);
        }
        gradeRepository.deleteById(gradeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeOutputDTO> getGradesByStudentId(Long studentId) {
         if (!studentRepository.existsById(studentId)) {
             throw new ResourceNotFoundException("Student not found with id: " + studentId);
         }
        return gradeRepository.findByStudentId(studentId)
                .stream()
                .map(this::toOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeOutputDTO> getGradesByCourseId(Long courseId) {
         if (!courseRepository.existsById(courseId)) {
             throw new ResourceNotFoundException("Course not found with id: " + courseId);
         }
        return gradeRepository.findByCourseId(courseId)
                .stream()
                .map(this::toOutputDto)
                .collect(Collectors.toList());
    }
}