# LMS (Learning Management System)

A comprehensive Learning Management System backend built with Spring Boot and JPA, providing complete student, course, section, and grade management functionality.

## Features

- **Student Management** - Add, update, delete, and search students
- **Course Management** - Create courses, assign instructors, manage schedules
- **Section Management** - Organize students into sections with capacity limits (40 students)
- **Grade Management** - Record grades, calculate averages, convert marks to GPA
- **RESTful APIs** - Ready to connect with any frontend application

## Tech Stack

- Java 17
- Spring Boot 4.0.5
- Spring Data JPA
- MySQL 8.0
- Maven

## Database Schema

- **students** - Student information (PK: seatNo)
- **courses** - Course details (PK: courseID)
- **sections** - Section organization (PK: sectionId)
- **grades** - Grade records (Composite PK: studentSeatNo + courseID + semester + academicYear)

