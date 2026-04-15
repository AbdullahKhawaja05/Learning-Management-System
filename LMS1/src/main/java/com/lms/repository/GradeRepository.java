package com.lms.repository;


import com.lms.entity.Grade;
import com.lms.entity.GradeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, GradeKey> {
    List<Grade> findByStudentSeatNo(int studentSeatNo);
    List<Grade> findByCourseCourseID(int courseID);
}