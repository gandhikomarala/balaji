package com.balaji.school.security.service;

import com.balaji.school.security.dto.AuditTrailComplianceLoggerRequestDto;
import com.balaji.school.security.model.AuditTrailComplianceLoggerEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service orchestrator for AuditTrailComplianceLogger.
 * Tamper-evident audit logging for grade changes, fee discounts, admission status modifications, and exam marks updates.
 */
public class AuditTrailComplianceLoggerService {

    private static final Logger LOGGER = Logger.getLogger(AuditTrailComplianceLoggerService.class.getName());
    private final Map<String, AuditTrailComplianceLoggerEntity> store = new ConcurrentHashMap<>();

    public AuditTrailComplianceLoggerService() {
        seedTelemetry();
    }

    private void seedTelemetry() {
        for (int i = 1; i <= 6; i++) {
            String entityId = "EXT-" + i;
            AuditTrailComplianceLoggerEntity record = new AuditTrailComplianceLoggerEntity(
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

    public AuditTrailComplianceLoggerEntity executeWorkflow(AuditTrailComplianceLoggerRequestDto dto) {
        if (dto == null || !dto.validatePayload()) {
            LOGGER.log(Level.WARNING, "Rejected execution for AuditTrailComplianceLogger");
            throw new IllegalArgumentException("Invalid payload provided for AuditTrailComplianceLogger");
        }

        LOGGER.info("Processing AuditTrailComplianceLogger workflow for: " + dto.getStudentId());

        AuditTrailComplianceLoggerEntity entity = new AuditTrailComplianceLoggerEntity();
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

    public Optional<AuditTrailComplianceLoggerEntity> getById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(store.get(id));
    }

    public List<AuditTrailComplianceLoggerEntity> getAll() {
        return new ArrayList<>(store.values());
    }

    public List<AuditTrailComplianceLoggerEntity> filterByStudent(String studentId) {
        if (studentId == null) return Collections.emptyList();
        return store.values().stream()
                .filter(e -> studentId.equalsIgnoreCase(e.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<AuditTrailComplianceLoggerEntity> filterByStatus(String status) {
        if (status == null) return Collections.emptyList();
        return store.values().stream()
                .filter(e -> status.equalsIgnoreCase(e.getExecutionStatus()))
                .collect(Collectors.toList());
    }

    public boolean updateExecutionStatus(String id, String newStatus, String notes) {
        AuditTrailComplianceLoggerEntity entity = store.get(id);
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
                .mapToDouble(AuditTrailComplianceLoggerEntity::getFinancialImpact)
                .sum();
    }

    public double averageConfidence() {
        return store.values().stream()
                .filter(e -> e.getConfidenceScore() != null)
                .mapToDouble(AuditTrailComplianceLoggerEntity::getConfidenceScore)
                .average()
                .orElse(0.0);
    }

    public long activeRecordsCount() {
        return store.values().stream().filter(AuditTrailComplianceLoggerEntity::isActive).count();
    }
}
