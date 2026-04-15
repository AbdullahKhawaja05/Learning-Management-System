package com.lms.service;

import com.lms.entity.Grade;
import com.lms.entity.GradeKey;
import com.lms.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public void addGrade(Grade grade) {
        if (grade == null) {
            return;
        }

        // Create and set the key
        GradeKey key = new GradeKey(
                grade.getStudentSeatNo(),
                grade.getCourseID(),
                grade.getSemester(),
                grade.getAcademicYear()
        );
        grade.setId(key);

        gradeRepository.save(grade);
    }

    public void addGrades(List<Grade> gradeList) {
        for (Grade grade : gradeList) {
            addGrade(grade);
        }
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public List<Grade> getGradesByStudent(int studentSeatNo) {
        return gradeRepository.findByStudentSeatNo(studentSeatNo);
    }

    public List<Grade> getGradesByCourse(int courseID) {
        return gradeRepository.findByCourseCourseID(courseID);
    }

    public Grade getGrade(int studentSeatNo, int courseID, int semester, int academicYear) {
        GradeKey key = new GradeKey(studentSeatNo, courseID, semester, academicYear);
        return gradeRepository.findById(key).orElse(null);
    }

    public void updateGrade(int studentSeatNo, int courseID, int semester, int academicYear, double newMarks) {
        GradeKey key = new GradeKey(studentSeatNo, courseID, semester, academicYear);
        Grade grade = gradeRepository.findById(key).orElse(null);
        if (grade != null) {
            grade.setMarks(newMarks);
            gradeRepository.save(grade);
        }
    }

    public boolean removeGrade(int studentSeatNo, int courseID, int semester, int academicYear) {
        GradeKey key = new GradeKey(studentSeatNo, courseID, semester, academicYear);
        if (gradeRepository.existsById(key)) {
            gradeRepository.deleteById(key);
            return true;
        }
        return false;
    }

    public void removeAllGrades() {
        gradeRepository.deleteAll();
    }

    public boolean removeStudentGrades(int studentSeatNo) {
        List<Grade> studentGrades = gradeRepository.findByStudentSeatNo(studentSeatNo);
        if (studentGrades.isEmpty()) return false;

        gradeRepository.deleteAll(studentGrades);
        return true;
    }

    public boolean removeCourseGrades(int courseID) {
        List<Grade> courseGrades = gradeRepository.findByCourseCourseID(courseID);
        if (courseGrades.isEmpty()) return false;

        gradeRepository.deleteAll(courseGrades);
        return true;
    }

    public boolean gradeExists(int studentSeatNo, int courseID, int semester, int academicYear) {
        GradeKey key = new GradeKey(studentSeatNo, courseID, semester, academicYear);
        return gradeRepository.existsById(key);
    }

    public int getTotalGrades() {
        return (int) gradeRepository.count();
    }

    public double getStudentAverage(int studentSeatNo) {
        List<Grade> studentGrades = gradeRepository.findByStudentSeatNo(studentSeatNo);
        if (studentGrades.isEmpty()) return 0.0;

        double sum = 0.0;
        for (Grade grade : studentGrades) {
            sum += grade.getMarks();
        }
        return sum / studentGrades.size();
    }

    public double getCourseAverage(int courseID) {
        List<Grade> courseGrades = gradeRepository.findByCourseCourseID(courseID);
        if (courseGrades.isEmpty()) return 0.0;

        double sum = 0.0;
        for (Grade grade : courseGrades) {
            sum += grade.getMarks();
        }
        return sum / courseGrades.size();
    }
}