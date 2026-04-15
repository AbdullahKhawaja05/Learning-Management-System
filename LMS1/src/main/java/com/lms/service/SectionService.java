package com.lms.service;

import com.lms.entity.Section;
import com.lms.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    // ============ CREATE ============

    public void addSection(Section section) {
        if (section == null) return;

        String sectionId = section.getSectionId();
        if (!sectionRepository.existsById(sectionId)) {
            sectionRepository.save(section);
            System.out.println("✓ Section added: " + section.getSectionName() + " (" + sectionId + ")");
        } else {
            System.out.println("⚠ Section " + sectionId + " already exists");
        }
    }

    // ============ READ ============

    public Optional<Section> getSection(String sectionId) {
        return sectionRepository.findById(sectionId);
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public List<Section> getSectionsByDegree(String degree) {
        if (degree == null || degree.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return sectionRepository.findByDegree(degree);
    }

    public List<Section> getSectionsByYear(int academicYear) {
        return sectionRepository.findByAcademicYear(academicYear);
    }

    public List<Section> getSectionsByTeacher(String teacher) {
        if (teacher == null || teacher.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return sectionRepository.findByHeadTeacherContaining(teacher);
    }

    public List<Section> searchSections(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String lowerSearch = searchTerm.toLowerCase();
        List<Section> allSections = sectionRepository.findAll();
        List<Section> result = new ArrayList<>();

        for (Section section : allSections) {
            if (section.getSectionId().toLowerCase().contains(lowerSearch) ||
                    section.getSectionName().toLowerCase().contains(lowerSearch) ||
                    section.getDegree().toLowerCase().contains(lowerSearch) ||
                    section.getHeadTeacher().toLowerCase().contains(lowerSearch)) {
                result.add(section);
            }
        }
        return result;
    }

    // ============ UPDATE ============

    public void updateSection(String sectionId, Section updatedSection) {
        if (!sectionRepository.existsById(sectionId)) {
            System.out.println("❌ Cannot update: Section " + sectionId + " not found");
            return;
        }

        if (updatedSection == null) {
            System.out.println("❌ Updated section cannot be null");
            return;
        }

        updatedSection.setSectionId(sectionId);
        sectionRepository.save(updatedSection);
        System.out.println("✓ Section updated: " + updatedSection.getSectionName());
    }

    public void updateHeadTeacher(String sectionId, String newTeacher) {
        Optional<Section> sectionOpt = sectionRepository.findById(sectionId);
        if (sectionOpt.isPresent()) {
            Section section = sectionOpt.get();
            String oldTeacher = section.getHeadTeacher();
            section.setHeadTeacher(newTeacher);
            sectionRepository.save(section);
            System.out.println("✓ Head teacher changed: " + oldTeacher + " → " + newTeacher);
        }
    }

    // ============ DELETE ============

    public boolean removeSection(String sectionId) {
        if (sectionRepository.existsById(sectionId)) {
            sectionRepository.deleteById(sectionId);
            System.out.println("✓ Section removed: " + sectionId);
            return true;
        } else {
            System.out.println("⚠ Section " + sectionId + " not found for removal");
            return false;
        }
    }

    public void removeAllSections() {
        long count = sectionRepository.count();
        sectionRepository.deleteAll();
        System.out.println("✓ All " + count + " sections removed");
    }

    public boolean removeSectionsByDegree(String degree) {
        List<Section> sectionsToRemove = sectionRepository.findByDegree(degree);
        if (sectionsToRemove.isEmpty()) {
            System.out.println("⚠ No sections found for stream: " + degree);
            return false;
        }

        sectionRepository.deleteAll(sectionsToRemove);
        System.out.println("✓ Removed " + sectionsToRemove.size() + " sections from " + degree + " stream");
        return true;
    }

    public boolean removeSectionsByYear(int academicYear) {
        List<Section> sectionsToRemove = sectionRepository.findByAcademicYear(academicYear);
        if (sectionsToRemove.isEmpty()) {
            System.out.println("⚠ No sections found for year: " + academicYear);
            return false;
        }

        sectionRepository.deleteAll(sectionsToRemove);
        System.out.println("✓ Removed " + sectionsToRemove.size() + " sections from year " + academicYear);
        return true;
    }

    public boolean removeEmptySections() {
        List<Section> allSections = sectionRepository.findAll();
        List<Section> emptySections = new ArrayList<>();

        for (Section section : allSections) {
            if (section.getEnrolledStudents().isEmpty()) {
                emptySections.add(section);
            }
        }

        if (emptySections.isEmpty()) {
            System.out.println("⚠ No empty sections found");
            return false;
        }

        sectionRepository.deleteAll(emptySections);
        System.out.println("✓ Removed " + emptySections.size() + " empty sections");
        return true;
    }

    // ============ UTILITY ============

    public boolean sectionExists(String sectionId) {
        return sectionRepository.existsById(sectionId);
    }

    public int getTotalSections() {
        return (int) sectionRepository.count();
    }

    public int getTotalStudents() {
        List<Section> allSections = sectionRepository.findAll();
        int total = 0;
        for (Section section : allSections) {
            total += section.getEnrolledStudents().size();
        }
        return total;
    }

    public int getAvailableSeats(String sectionId) {
        Optional<Section> sectionOpt = sectionRepository.findById(sectionId);
        if (sectionOpt.isPresent()) {
            return 40 - sectionOpt.get().getEnrolledStudents().size();
        }
        return 0;
    }

    // ============ SECTION-SPECIFIC OPERATIONS ============

    public boolean enrollStudentInSection(String sectionId, int seatNo) {
        Optional<Section> sectionOpt = sectionRepository.findById(sectionId);
        if (sectionOpt.isEmpty()) {
            return false;
        }

        Section section = sectionOpt.get();
        if (section.getEnrolledStudents().size() >= 40) {
            System.out.println("❌ Section " + sectionId + " is full!");
            return false;
        }

        System.out.println("✓ Student (Seat " + seatNo + ") enrolled in " + sectionId);
        return true;
    }

    public List<Section> getSectionsWithAvailableSeats() {
        List<Section> allSections = sectionRepository.findAll();
        List<Section> result = new ArrayList<>();
        for (Section section : allSections) {
            if (section.getEnrolledStudents().size() < 40) {
                result.add(section);
            }
        }
        return result;
    }

    public List<Section> getSectionsNeedingStudents() {
        List<Section> allSections = sectionRepository.findAll();
        List<Section> result = new ArrayList<>();
        for (Section section : allSections) {
            if (section.getEnrolledStudents().size() < 20) {
                result.add(section);
            }
        }
        return result;
    }
}
