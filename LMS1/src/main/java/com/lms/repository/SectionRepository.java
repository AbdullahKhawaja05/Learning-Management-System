package com.lms.repository;



import com.lms.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SectionRepository extends JpaRepository<Section, String> {
    List<Section> findByDegree(String degree);
    List<Section> findByAcademicYear(int academicYear);
    List<Section> findByHeadTeacherContaining(String headTeacher);
}