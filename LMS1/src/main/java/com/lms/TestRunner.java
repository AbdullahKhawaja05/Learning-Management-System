package com.lms;

import com.lms.entity.*;
import com.lms.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class TestRunner {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Lms1Application.class, args);

        StudentService studentService = context.getBean(StudentService.class);
        CourseService courseService = context.getBean(CourseService.class);
        SectionService sectionService = context.getBean(SectionService.class);
        GradeService gradeService = context.getBean(GradeService.class);

        System.out.println("\n========== TESTING BACKEND ==========\n");

        // ============ TEST 1: Add Sections ============
        System.out.println("--- Adding Sections ---");

        Section sciSection = new Section("SCI-A-2024", "Science A", "Science", 2024, "Dr. Smith");
        Section comSection = new Section("COM-B-2024", "Commerce B", "Commerce", 2024, "Dr. Johnson");

        sectionService.addSection(sciSection);
        sectionService.addSection(comSection);

        // ============ TEST 2: Add Students ============
        System.out.println("\n--- Adding Students ---");

        Student s1 = new Student(1001, "John Doe", "SCI-A-2024", 1, "Robert Doe", "john@email.com");
        Student s2 = new Student(1002, "Jane Smith", "SCI-A-2024", 1, "William Smith", "jane@email.com");
        Student s3 = new Student(1003, "Bob Johnson", "COM-B-2024", 2, "James Johnson", "bob@email.com");

        studentService.addStudent(s1);
        studentService.addStudent(s2);
        studentService.addStudent(s3);

        System.out.println("Students added: " + studentService.getSize());

        // ============ TEST 3: Add Courses ============
        System.out.println("\n--- Adding Courses ---");

        Course math = new Course(101, "Calculus", "Dr. Adams", 3, 201, "MWF 10:00");
        Course physics = new Course(102, "Physics", "Dr. Brown", 4, 202, "TTH 9:00");

        courseService.addCourse(math);
        courseService.addCourse(physics);

        System.out.println("Courses added: " + courseService.getTotalCourses());

        // ============ TEST 4: Add Grades ============
        System.out.println("\n--- Adding Grades ---");

        // Create grades using default constructor + setters
        Grade g1 = new Grade();
        g1.setStudentSeatNo(1001);
        g1.setCourseID(101);
        g1.setSemester(1);
        g1.setAcademicYear(2024);
        g1.setMarks(85.5);
        gradeService.addGrade(g1);

        Grade g2 = new Grade();
        g2.setStudentSeatNo(1001);
        g2.setCourseID(102);
        g2.setSemester(1);
        g2.setAcademicYear(2024);
        g2.setMarks(92.0);
        gradeService.addGrade(g2);

        Grade g3 = new Grade();
        g3.setStudentSeatNo(1002);
        g3.setCourseID(101);
        g3.setSemester(1);
        g3.setAcademicYear(2024);
        g3.setMarks(78.0);
        gradeService.addGrade(g3);

        System.out.println("Grades added: " + gradeService.getTotalGrades());

        // ============ TEST 5: Retrieve Data ============
        System.out.println("\n--- Retrieving Data ---");

        System.out.println("\nAll Students:");
        for (Student s : studentService.findAll()) {
            System.out.println("  " + s.getSeatNo() + ": " + s.getName());
        }

        System.out.println("\nAll Courses:");
        for (Course c : courseService.getAllCourses()) {
            System.out.println("  " + c.getCourseID() + ": " + c.getCourseName());
        }

        System.out.println("\nAll Grades:");
        for (Grade g : gradeService.getAllGrades()) {
            System.out.println("  Student " + g.getStudentSeatNo() + ", Course " + g.getCourseID() + ": " + g.getMarks());
        }

        // ============ TEST 6: Calculations ============
        System.out.println("\n--- Calculations ---");

        double johnAvg = gradeService.getStudentAverage(1001);
        System.out.println("John Doe's Average: " + johnAvg);

        double mathAvg = gradeService.getCourseAverage(101);
        System.out.println("Calculus Average: " + mathAvg);

        // ============ FINAL SUMMARY ============
        System.out.println("\n========== TEST SUMMARY ==========");
        System.out.println("Total Students: " + studentService.getSize());
        System.out.println("Total Courses: " + courseService.getTotalCourses());
        System.out.println("Total Sections: " + sectionService.getTotalSections());
        System.out.println("Total Grades: " + gradeService.getTotalGrades());

        System.out.println("\n✅ BACKEND TEST COMPLETED SUCCESSFULLY!");

        context.close();
    }
}