package com.lms.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sections")
public class Section {


    @Id
    @Column(name = "section_id")
    private String sectionId;      // "SCI-A-2024"
    private String sectionName;    // "Science A 2024"
    private String degree;       // "Science", "Commerce", "Arts"
    private int academicYear;      // 2024, 2025
    private String headTeacher;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> enrolledStudents = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "section_courses",
            joinColumns = @JoinColumn(name = "section_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> assignedCourses;

    public Section(){};
    
    public Section(String sectionId, String sectionName, String degree,
                   int academicYear, String headTeacher) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.degree = degree;
        this.academicYear = academicYear;
        this.headTeacher = headTeacher;
        this.enrolledStudents = new ArrayList<>();
        this.assignedCourses = new ArrayList<>();
    }
    
    // ============ STUDENT MANAGEMENT ============
    
    /**
     * Enroll a student in this section
     */
    public boolean enrollStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
            student.setSection(this);
            return true;
        }
        return false;
    }
    
    /**
     * Remove a student from this section
     */
    public boolean removeStudent(Student student) {
    if(enrolledStudents.remove(student)) {
        student.setSection(null);
        return true;
    }
    return false;
    }
    
    /**
     * Check if student is enrolled
     */
    public boolean isStudentEnrolled(Student student) {
        return enrolledStudents.contains(student);
    }
    
    /**
     * Get number of students in section
     */
    public int getStudentCount() {
        return enrolledStudents.size();
    }
    
    /**
     * Get all enrolled students
     */
    public List<Student> getEnrolledStudents() {
        return new ArrayList<>(enrolledStudents);
    }
    
    // ============ COURSE MANAGEMENT ============
    
    /**
     * Assign a course to this section
     */
    public boolean assignCourse(Course courseId) {
        if (!assignedCourses.contains(courseId)) {
            assignedCourses.add(courseId);
            return true;
        }
        return false;
    }
    
    /**
     * Remove a course from this section
     */
    public boolean removeCourse(Course courseId) {
        return assignedCourses.remove(courseId);
    }
    
    /**
     * Check if course is assigned
     */
    public boolean isCourseAssigned(Course courseId) {
        return assignedCourses.contains(courseId);
    }
    
    /**
     * Get number of assigned courses
     */
    public int getCourseCount() {
        return assignedCourses.size();
    }
    
    /**
     * Get all assigned courses
     */
    public List<Course> getAssignedCourses() {
        return new ArrayList<>(assignedCourses);
    }
    
    // ============ UTILITY METHODS ============
    
    /**
     * Check if section is full (max 40 students)
     */
    public boolean isFull() {
        return getStudentCount() >= 40;
    }
    
    /**
     * Check if section is empty
     */
    public boolean isEmpty() {
        return enrolledStudents.isEmpty();
    }
    
    /**
     * Get section information
     */
    public String getSectionInfo() {
        return String.format("%s: %s | Degree: %s | Year: %d | Students: %d/%d | Courses: %d",
            sectionId, sectionName, degree, academicYear, 
            getStudentCount(), 40, getCourseCount());
    }
    
    /**
     * Get detailed section information
     */
    public String getDetailedInfo() {
        return String.format("""
            Section ID: %s
            Name: %s
            Stream: %s | Academic Year: %d
            Head Teacher: %s
            Students Enrolled: %d/%d
            Courses Assigned: %d
            """,
            sectionId, sectionName, degree, academicYear,
            headTeacher, getStudentCount(), 40, getCourseCount());
    }
    
    // ============ GETTERS AND SETTERS ============
    public String getSectionId() { return sectionId; }
    public void setSectionId(String sectionId) { this.sectionId = sectionId; }

    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public int getAcademicYear() { return academicYear; }
    public void setAcademicYear(int academicYear) { this.academicYear = academicYear; }

    public String getHeadTeacher() { return headTeacher; }
    public void setHeadTeacher(String headTeacher) { this.headTeacher = headTeacher; }

    public void setEnrolledStudents(List<Student> enrolledStudents) { this.enrolledStudents = enrolledStudents; }

    public void setAssignedCourses(List<Course> assignedCourses) { this.assignedCourses = assignedCourses; }

    @Override
    public String toString() {
        return getSectionInfo();
    }
}

