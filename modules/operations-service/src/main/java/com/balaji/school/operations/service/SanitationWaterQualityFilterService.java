package com.balaji.school.operations.service;

import com.balaji.school.operations.dto.SanitationWaterQualityFilterRequestDto;
import com.balaji.school.operations.model.SanitationWaterQualityFilterEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service orchestrator for SanitationWaterQualityFilter.
 * RO drinking water plant TDS testing logs, overhead tank chlorination schedule, and campus hygiene audit inspection.
 */
public class SanitationWaterQualityFilterService {

    private static final Logger LOGGER = Logger.getLogger(SanitationWaterQualityFilterService.class.getName());
    private final Map<String, SanitationWaterQualityFilterEntity> repository = new ConcurrentHashMap<>();

    public SanitationWaterQualityFilterService() {
        initializeDefaultRecords();
    }

    private void initializeDefaultRecords() {
        for (int i = 1; i <= 6; i++) {
            String entityId = "BALAJI-" + i;
            SanitationWaterQualityFilterEntity record = new SanitationWaterQualityFilterEntity(
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

    public SanitationWaterQualityFilterEntity registerEntry(SanitationWaterQualityFilterRequestDto dto) {
        if (dto == null || !dto.validatePayload()) {
            LOGGER.log(Level.WARNING, "Rejected invalid registration attempt for SanitationWaterQualityFilter");
            throw new IllegalArgumentException("Invalid payload provided for SanitationWaterQualityFilter registration");
        }

        LOGGER.info("Executing SanitationWaterQualityFilter workflow for student: " + dto.getStudentId());

        SanitationWaterQualityFilterEntity entity = new SanitationWaterQualityFilterEntity();
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

    public Optional<SanitationWaterQualityFilterEntity> getById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(repository.get(id));
    }

    public List<SanitationWaterQualityFilterEntity> getAll() {
        return new ArrayList<>(repository.values());
    }

    public List<SanitationWaterQualityFilterEntity> filterByStudent(String studentId) {
        if (studentId == null) return Collections.emptyList();
        return repository.values().stream()
                .filter(e -> studentId.equalsIgnoreCase(e.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<SanitationWaterQualityFilterEntity> filterByTeacher(String teacherId) {
        if (teacherId == null) return Collections.emptyList();
        return repository.values().stream()
                .filter(e -> teacherId.equalsIgnoreCase(e.getTeacherId()))
                .collect(Collectors.toList());
    }

    public List<SanitationWaterQualityFilterEntity> filterByStatus(String status) {
        if (status == null) return Collections.emptyList();
        return repository.values().stream()
                .filter(e -> status.equalsIgnoreCase(e.getStatus()))
                .collect(Collectors.toList());
    }

    public boolean updateStatus(String id, String newStatus, String operatorNotes) {
        SanitationWaterQualityFilterEntity entity = repository.get(id);
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
                .mapToDouble(SanitationWaterQualityFilterEntity::getMonetaryAmount)
                .sum();
    }

    public double computeMeanPrimaryScore() {
        return repository.values().stream()
                .filter(e -> e.getPrimaryScore() != null)
                .mapToDouble(SanitationWaterQualityFilterEntity::getPrimaryScore)
                .average()
                .orElse(0.0);
    }

    public long totalActiveRecords() {
        return repository.values().stream().filter(SanitationWaterQualityFilterEntity::isActive).count();
    }
}
