package com.lms.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @Column(name = "course_id")
    private int courseID;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "instructor")
    private String instructor;

    @Column(name = "credit_hours")
    private int creditHours;

    @Column(name = "room_no")
    private int roomNo;

    @Column(name = "schedule")
    private String schedule;

    // ✅ Tracks grades instead of direct student enrollment
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grade> grades = new ArrayList<>();

    public Course() {}

    public Course(int courseID, String courseName, String instructor,
                  int creditHours, int roomNo, String schedule) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.instructor = instructor;
        this.creditHours = creditHours;
        this.roomNo = roomNo;
        this.schedule = schedule;
    }

    // ============ GRADE MANAGEMENT ============

    public void addGrade(Grade grade) {
        if (grade != null && !grades.contains(grade)) {
            grades.add(grade);
        }
    }

    public boolean removeGrade(Grade grade) {
        return grades.remove(grade);
    }

    public int getEnrollmentCount() {
        return grades.size();
    }

    public List<Grade> getGrades() {
        return new ArrayList<>(grades);
    }

    // ============ GETTERS AND SETTERS ============

    public int getCourseID() { return courseID; }
    public void setCourseID(int courseID) { this.courseID = courseID; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }

    public int getRoomNo() { return roomNo; }
    public void setRoomNo(int roomNo) { this.roomNo = roomNo; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public void setGrades(List<Grade> grades) { this.grades = grades; }

    @Override
    public String toString() {
        return String.format("Course [id=%d, name=%s, instructor=%s, credits=%d, room=%d, schedule=%s]",
                courseID, courseName, instructor, creditHours, roomNo, schedule);
    }
} // ✅ closing brace


    /*    ## Summary of Changes

**1. Removed `@ManyToMany` with Student** — redundant since `Grade` already links students to courses. Keeping both would cause confusion and duplicate data.

        **2. Added `@OneToMany` to `Grade`** — now `Course` tracks its grades, which indirectly tracks enrolled students.

**3. Added `@Column` on all fields** — explicit mapping.

        **4. Fixed constructor parameter order** — `courseID` moved to first parameter to match field declaration order, cleaner to read.

**5. Added `toString()`** and closing `}`.

        ---

        ## Your Entity Relationships Are Now Complete
```
Section  ──OneToMany──▶  Student  ◀──ManyToOne──  Grade  ◀──ManyToOne──  Course
                                                      │
                                                              (composite key:
seatNo + courseID
                                                 + semester + year) */