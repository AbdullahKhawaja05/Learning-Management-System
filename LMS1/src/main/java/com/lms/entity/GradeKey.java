package com.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

// ===== GRADEKEY CLASS =====
@Embeddable
public class GradeKey implements Serializable {

    @Column(name = "student_seat_no")
    private int studentSeatNo;

    @Column(name = "course_id")
    private int courseID;

    @Column(name = "semester")
    private int semester;

    @Column(name = "academic_year")
    private int academicYear;

    public GradeKey() {}

    public GradeKey(int studentSeatNo, int courseID, int semester, int academicYear) {
        this.studentSeatNo = studentSeatNo;
        this.courseID = courseID;
        this.semester = semester;
        this.academicYear = academicYear;
    }

    public int getStudentSeatNo() { return studentSeatNo; }
    public int getCourseID() { return courseID; }
    public int getSemester() { return semester; }
    public int getAcademicYear() { return academicYear; }

    public void setStudentSeatNo(int studentSeatNo) { this.studentSeatNo = studentSeatNo; }
    public void setCourseID(int courseID) { this.courseID = courseID; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setAcademicYear(int academicYear) { this.academicYear = academicYear; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GradeKey)) return false;
        GradeKey that = (GradeKey) o;
        return studentSeatNo == that.studentSeatNo &&
                courseID == that.courseID &&
                semester == that.semester &&
                academicYear == that.academicYear;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentSeatNo, courseID, semester, academicYear);
    }
}
