package com.lms.service;

import com.lms.entity.Student;
import com.lms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public boolean addStudent(Student newStudent) {
        if (newStudent == null) {
            return false;
        }

        if (findBySeatNo(newStudent.getSeatNo()).isPresent()) {
            return false;
        }

        studentRepository.save(newStudent);
        return true;
    }

    public Optional<Student> findBySeatNo(int seatNo) {
        return studentRepository.findBySeatNo(seatNo);
    }

    public List<Student> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return studentRepository.findByNameContaining(name);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public boolean updateStudent(int seatNo, String newName, String section, int year, String fatherName, String email) {
        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }

        Optional<Student> existingOpt = findBySeatNo(seatNo);
        if (existingOpt.isPresent()) {
            Student updated = new Student(seatNo, newName, section, year, fatherName, email);
            studentRepository.save(updated);
            return true;
        }
        return false;
    }

    public boolean updateSeatNo(int oldSeatNo, int newSeatNo, String name, String section, int year, String fatherName, String email) {
        Optional<Student> existingOpt = findBySeatNo(oldSeatNo);
        if (existingOpt.isEmpty()) {
            return false;
        }

        if (findBySeatNo(newSeatNo).isPresent()) {
            return false;
        }

        Student updated = new Student(newSeatNo, name, section, year, fatherName, email);
        studentRepository.deleteById(oldSeatNo);
        studentRepository.save(updated);
        return true;
    }

    public boolean deleteStudent(int seatNo) {
        if (studentRepository.existsById(seatNo)) {
            studentRepository.deleteById(seatNo);
            return true;
        }
        return false;
    }

    public List<Student> sortBySeatNo() {
        List<Student> sorted = studentRepository.findAll();
        sorted.sort(Comparator.comparingInt(Student::getSeatNo));
        return sorted;
    }

    public List<Student> sortByName() {
        List<Student> sorted = studentRepository.findAll();
        sorted.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }

    public int getSize() {
        return (int) studentRepository.count();
    }


}