package com.lms.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

// ===== GRADE CLASS =====
@Entity
@Table(name = "grades")
public class Grade {

    @EmbeddedId
    private GradeKey id;

    @Column(name = "marks")
    private double marks;

    @ManyToOne
    @MapsId("studentSeatNo")
    @JoinColumn(name = "student_seat_no")
    private Student student;

    @ManyToOne
    @MapsId("courseID")
    @JoinColumn(name = "course_id")
    private Course course;

    public Grade() {}

    public Grade(Student student, Course course, int semester, int academicYear, double marks) {
        this.id = new GradeKey(student.getSeatNo(), course.getCourseID(), semester, academicYear);
        this.student = student;
        this.course = course;
        this.marks = marks;
    }

    // ============ GETTERS AND SETTERS ============

    public GradeKey getId() { return id; }
    public void setId(GradeKey id) { this.id = id; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    // ============ HELPER METHODS ============

    public int getStudentSeatNo() { return id.getStudentSeatNo(); }
    public int getCourseID() { return id.getCourseID(); }
    public int getSemester() { return id.getSemester(); }
    public int getAcademicYear() { return id.getAcademicYear(); }

    // ============ GRADE CALCULATION ============

    public String getLetterGrade() {
        if (marks >= 90) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 70) return "B+";
        if (marks >= 60) return "B";
        if (marks >= 50) return "C";
        return "F";
    }

    public boolean isPassing() {
        return marks >= 50;
    }

    @Override
    public String toString() {
        return String.format("Grade[student=%d, course=%d, semester=%d, year=%d, marks=%.2f, grade=%s]",
                getStudentSeatNo(), getCourseID(), getSemester(), getAcademicYear(),
                marks, getLetterGrade());
    }

    public void setStudentSeatNo(int i) {
    }

    public void setCourseID(int i) {
    }

    public void setSemester(int i) {
    }

    public void setAcademicYear(int i) {
    }
} // ✅ closing brace