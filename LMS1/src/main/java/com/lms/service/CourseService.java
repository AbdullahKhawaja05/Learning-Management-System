package com.lms.service;

import com.lms.entity.Course;
import com.lms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // ============ CREATE ============
    public void addCourse(Course course) {
        if (course == null) return;
        if (!courseRepository.existsById(course.getCourseID())) {
            courseRepository.save(course);
        }
    }

    // ============ READ ============
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCourseByName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return courseRepository.findByCourseNameContaining(searchTerm);
    }

    public List<Course> getCourseByInstructor(String instructor) {
        if (instructor == null || instructor.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return courseRepository.findByInstructorContaining(instructor);
    }

    public Optional<Course> getCourseById(int courseID) {
        return courseRepository.findById(courseID);
    }

    // ============ UPDATE ============
    public void updateCourse(int courseID, Course updatedCourse) {
        if (courseRepository.existsById(courseID) && updatedCourse != null) {
            updatedCourse.setCourseID(courseID);
            courseRepository.save(updatedCourse);
        }
    }

    public void updateInstructor(int courseID, String newInstructor) {
        Optional<Course> courseOpt = courseRepository.findById(courseID);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.setInstructor(newInstructor);
            courseRepository.save(course);
        }
    }

    public void updateSchedule(int courseID, String newSchedule) {
        Optional<Course> courseOpt = courseRepository.findById(courseID);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.setSchedule(newSchedule);
            courseRepository.save(course);
        }
    }

    public void updateRoom(int courseID, int newRoomNo) {
        Optional<Course> courseOpt = courseRepository.findById(courseID);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.setRoomNo(newRoomNo);
            courseRepository.save(course);
        }
    }

    // ============ DELETE ============
    public boolean removeCourse(int courseID) {
        if (courseRepository.existsById(courseID)) {
            courseRepository.deleteById(courseID);
            return true;
        }
        return false;
    }

    public void removeAllCourses() {
        courseRepository.deleteAll();
    }

    public boolean removeCoursesByInstructor(String instructor) {
        List<Course> coursesToRemove = courseRepository.findByInstructorContaining(instructor);
        if (coursesToRemove.isEmpty()) return false;

        courseRepository.deleteAll(coursesToRemove);
        return true;
    }

    public boolean removeEmptyCourses() {
        List<Course> allCourses = courseRepository.findAll();
        List<Course> emptyCourses = new ArrayList<>();

        for (Course course : allCourses) {
            if (course.getEnrollmentCount() == 0) {
                emptyCourses.add(course);
            }
        }

        if (emptyCourses.isEmpty()) return false;

        courseRepository.deleteAll(emptyCourses);
        return true;
    }

    // ============ UTILITY ============
    public boolean courseExists(int courseID) {
        return courseRepository.existsById(courseID);
    }

    public int getTotalCourses() {
        return (int) courseRepository.count();
    }

    public int getTotalEnrolledStudents() {
        List<Course> allCourses = courseRepository.findAll();
        int total = 0;
        for (Course course : allCourses) {
            total += course.getEnrollmentCount();
        }
        return total;
    }
}
