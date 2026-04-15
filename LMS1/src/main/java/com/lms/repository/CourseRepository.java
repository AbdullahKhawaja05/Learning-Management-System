package com.lms.repository;



import com.lms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    // Find by course name (partial match)
    List<Course> findByCourseNameContaining(String courseName);

    // Find by instructor (partial match)
    List<Course> findByInstructorContaining(String instructor);

    // Find by credit hours
    List<Course> findByCreditHours(int creditHours);

    // Find by room number
    List<Course> findByRoomNo(int roomNo);
}
