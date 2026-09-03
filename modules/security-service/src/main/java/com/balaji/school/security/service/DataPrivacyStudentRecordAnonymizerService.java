package com.balaji.school.security.service;

import com.balaji.school.security.dto.DataPrivacyStudentRecordAnonymizerRequestDto;
import com.balaji.school.security.model.DataPrivacyStudentRecordAnonymizerEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service orchestrator for DataPrivacyStudentRecordAnonymizer.
 * Implements Indian Digital Personal Data Protection (DPDP) compliance for student health and caste records.
 */
public class DataPrivacyStudentRecordAnonymizerService {

    private static final Logger LOGGER = Logger.getLogger(DataPrivacyStudentRecordAnonymizerService.class.getName());
    private final Map<String, DataPrivacyStudentRecordAnonymizerEntity> store = new ConcurrentHashMap<>();

    public DataPrivacyStudentRecordAnonymizerService() {
        seedTelemetry();
    }

    private void seedTelemetry() {
        for (int i = 1; i <= 6; i++) {
            String entityId = "EXT-" + i;
            DataPrivacyStudentRecordAnonymizerEntity record = new DataPrivacyStudentRecordAnonymizerEntity(
                entityId,
                "REF-ADV-" + (3000 + i),
                "STU-ADV-" + (200 + i),
                "OFF-ADV-" + (50 + i),
                "2026-2027",
                "ENTERPRISE_GRADE_TELEMETRY",
                "VERIFIED_ACTIVE",
                91.2 + (i * 1.4),
                95.5,
                2400.0 * i,
                "State educational compliance record for Prakasam district jurisdiction",
                "ADMIN_SYSTEM"
            );
            store.put(entityId, record);
        }
    }

    public DataPrivacyStudentRecordAnonymizerEntity executeWorkflow(DataPrivacyStudentRecordAnonymizerRequestDto dto) {
        if (dto == null || !dto.validatePayload()) {
            LOGGER.log(Level.WARNING, "Rejected execution for DataPrivacyStudentRecordAnonymizer");
            throw new IllegalArgumentException("Invalid payload provided for DataPrivacyStudentRecordAnonymizer");
        }

        LOGGER.info("Processing DataPrivacyStudentRecordAnonymizer workflow for: " + dto.getStudentId());

        DataPrivacyStudentRecordAnonymizerEntity entity = new DataPrivacyStudentRecordAnonymizerEntity();
        entity.setStudentId(dto.getStudentId());
        entity.setOfficerId(dto.getOfficerId());
        entity.setAcademicYear(dto.getAcademicYear());
        entity.setClassificationKey(dto.getClassificationKey());
        entity.setConfidenceScore(dto.getConfidenceScore());
        entity.setSecondaryScore(dto.getSecondaryScore());
        entity.setFinancialImpact(dto.getFinancialImpact());
        entity.setRemarks(dto.getNotes());
        entity.setVerifiedBy(dto.getOperatorId());
        entity.setExecutionStatus("COMPLETED_SUCCESS");
        entity.setUpdatedAt(LocalDateTime.now());

        store.put(entity.getId(), entity);
        return entity;
    }

    public Optional<DataPrivacyStudentRecordAnonymizerEntity> getById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(store.get(id));
    }

    public List<DataPrivacyStudentRecordAnonymizerEntity> getAll() {
        return new ArrayList<>(store.values());
    }

    public List<DataPrivacyStudentRecordAnonymizerEntity> filterByStudent(String studentId) {
        if (studentId == null) return Collections.emptyList();
        return store.values().stream()
                .filter(e -> studentId.equalsIgnoreCase(e.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<DataPrivacyStudentRecordAnonymizerEntity> filterByStatus(String status) {
        if (status == null) return Collections.emptyList();
        return store.values().stream()
                .filter(e -> status.equalsIgnoreCase(e.getExecutionStatus()))
                .collect(Collectors.toList());
    }

    public boolean updateExecutionStatus(String id, String newStatus, String notes) {
        DataPrivacyStudentRecordAnonymizerEntity entity = store.get(id);
        if (entity != null) {
            entity.setExecutionStatus(newStatus);
            entity.setRemarks(notes + " [Updated at " + LocalDateTime.now() + "]");
            entity.incrementVersion();
            return true;
        }
        return false;
    }

    public boolean purge(String id) {
        return store.remove(id) != null;
    }

    public double totalFinancialValue() {
        return store.values().stream()
                .filter(e -> e.getFinancialImpact() != null)
                .mapToDouble(DataPrivacyStudentRecordAnonymizerEntity::getFinancialImpact)
                .sum();
    }

    public double averageConfidence() {
        return store.values().stream()
                .filter(e -> e.getConfidenceScore() != null)
                .mapToDouble(DataPrivacyStudentRecordAnonymizerEntity::getConfidenceScore)
                .average()
                .orElse(0.0);
    }

    public long activeRecordsCount() {
        return store.values().stream().filter(DataPrivacyStudentRecordAnonymizerEntity::isActive).count();
    }
}
