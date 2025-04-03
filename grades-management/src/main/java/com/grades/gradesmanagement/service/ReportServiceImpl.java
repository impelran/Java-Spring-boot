package com.grades.gradesmanagement.service;
import com.grades.gradesmanagement.dto.AverageGradeDTO;
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

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final GradeRepository gradeRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public AverageGradeDTO getAverageGradeForCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
             .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        List<Grade> grades = gradeRepository.findByCourseId(courseId);
        if (grades.isEmpty()) {
            return new AverageGradeDTO(course.getName(), 0.0, 0);
        }

        double average = grades.stream()
                               .mapToDouble(Grade::getScore)
                               .average()
                               .orElse(0.0);

        average = Math.round(average * 100.0) / 100.0;

        return new AverageGradeDTO(course.getName(), average, grades.size());
    }

    @Override
    @Transactional(readOnly = true)
    public AverageGradeDTO getAverageGradeForStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
             .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        List<Grade> grades = gradeRepository.findByStudentId(studentId);
        if (grades.isEmpty()) {
             return new AverageGradeDTO(student.getFirstName() + " " + student.getLastName(), 0.0, 0);
        }

        double average = grades.stream()
                               .mapToDouble(Grade::getScore)
                               .average()
                               .orElse(0.0);

        average = Math.round(average * 100.0) / 100.0;

        String studentName = student.getFirstName() + " " + student.getLastName();
        return new AverageGradeDTO(studentName, average, grades.size());
    }
}