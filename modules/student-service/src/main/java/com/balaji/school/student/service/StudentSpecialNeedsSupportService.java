package com.balaji.school.student.service;

import com.balaji.school.student.dto.StudentSpecialNeedsSupportRequestDto;
import com.balaji.school.student.model.StudentSpecialNeedsSupportEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service orchestrator for StudentSpecialNeedsSupport.
 * Inclusive education support, visual/hearing aid accommodation, scribe allocations, and counselor notes.
 */
public class StudentSpecialNeedsSupportService {

    private static final Logger LOGGER = Logger.getLogger(StudentSpecialNeedsSupportService.class.getName());
    private final Map<String, StudentSpecialNeedsSupportEntity> repository = new ConcurrentHashMap<>();

    public StudentSpecialNeedsSupportService() {
        initializeDefaultRecords();
    }

    private void initializeDefaultRecords() {
        for (int i = 1; i <= 6; i++) {
            String entityId = "BALAJI-" + i;
            StudentSpecialNeedsSupportEntity record = new StudentSpecialNeedsSupportEntity(
                entityId,
                "REF-2026-" + (2000 + i),
                "STU-2026-" + (100 + i),
                "TCH-2026-" + (10 + i),
                "2026-2027",
                "CLASS_X_AP_BOARD",
                "VERIFIED_APPROVED",
                88.5 + (i * 1.8),
                94.0,
                1500.0 * i,
                "Official verified record from Santhamaguluru Mandal Educational Office",
                "SYSTEM_CONTROLLER"
            );
            repository.put(entityId, record);
        }
    }

    public StudentSpecialNeedsSupportEntity registerEntry(StudentSpecialNeedsSupportRequestDto dto) {
        if (dto == null || !dto.validatePayload()) {
            LOGGER.log(Level.WARNING, "Rejected invalid registration attempt for StudentSpecialNeedsSupport");
            throw new IllegalArgumentException("Invalid payload provided for StudentSpecialNeedsSupport registration");
        }

        LOGGER.info("Executing StudentSpecialNeedsSupport workflow for student: " + dto.getStudentId());

        StudentSpecialNeedsSupportEntity entity = new StudentSpecialNeedsSupportEntity();
        entity.setStudentId(dto.getStudentId());
        entity.setTeacherId(dto.getTeacherId());
        entity.setAcademicYear(dto.getAcademicYear());
        entity.setClassificationCode(dto.getClassificationCode());
        entity.setPrimaryScore(dto.getPrimaryScore());
        entity.setSecondaryScore(dto.getSecondaryScore());
        entity.setMonetaryAmount(dto.getMonetaryAmount());
        entity.setRemarks(dto.getNotes());
        entity.setVerifiedBy(dto.getOperatorId());
        entity.setStatus("PROCESSED_SUCCESS");
        entity.setUpdatedAt(LocalDateTime.now());

        repository.put(entity.getId(), entity);
        return entity;
    }

    public Optional<StudentSpecialNeedsSupportEntity> getById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(repository.get(id));
    }

    public List<StudentSpecialNeedsSupportEntity> getAll() {
        return new ArrayList<>(repository.values());
    }

    public List<StudentSpecialNeedsSupportEntity> filterByStudent(String studentId) {
        if (studentId == null) return Collections.emptyList();
        return repository.values().stream()
                .filter(e -> studentId.equalsIgnoreCase(e.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<StudentSpecialNeedsSupportEntity> filterByTeacher(String teacherId) {
        if (teacherId == null) return Collections.emptyList();
        return repository.values().stream()
                .filter(e -> teacherId.equalsIgnoreCase(e.getTeacherId()))
                .collect(Collectors.toList());
    }

    public List<StudentSpecialNeedsSupportEntity> filterByStatus(String status) {
        if (status == null) return Collections.emptyList();
        return repository.values().stream()
                .filter(e -> status.equalsIgnoreCase(e.getStatus()))
                .collect(Collectors.toList());
    }

    public boolean updateStatus(String id, String newStatus, String operatorNotes) {
        StudentSpecialNeedsSupportEntity entity = repository.get(id);
        if (entity != null) {
            entity.setStatus(newStatus);
            entity.setRemarks(operatorNotes + " [Timestamp: " + LocalDateTime.now() + "]");
            entity.incrementVersion();
            return true;
        }
        return false;
    }

    public boolean removeEntry(String id) {
        return repository.remove(id) != null;
    }

    public double computeCumulativeAmount() {
        return repository.values().stream()
                .filter(e -> e.getMonetaryAmount() != null)
                .mapToDouble(StudentSpecialNeedsSupportEntity::getMonetaryAmount)
                .sum();
    }

    public double computeMeanPrimaryScore() {
        return repository.values().stream()
                .filter(e -> e.getPrimaryScore() != null)
                .mapToDouble(StudentSpecialNeedsSupportEntity::getPrimaryScore)
                .average()
                .orElse(0.0);
    }

    public long totalActiveRecords() {
        return repository.values().stream().filter(StudentSpecialNeedsSupportEntity::isActive).count();
    }
}
