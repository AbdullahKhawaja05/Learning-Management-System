package com.lms.repository;

import com.lms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    // Spring gives ALL basic CRUD automatically

    // You ONLY add these 2 custom searches:
    Optional<Student> findBySeatNo(int seatNo);      // Custom
    List<Student> findByNameContaining(String name); // Custom
}