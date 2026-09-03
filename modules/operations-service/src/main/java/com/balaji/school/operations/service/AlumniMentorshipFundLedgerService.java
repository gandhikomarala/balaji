package com.balaji.school.operations.service;

import com.balaji.school.operations.dto.AlumniMentorshipFundLedgerRequestDto;
import com.balaji.school.operations.model.AlumniMentorshipFundLedgerEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service orchestrator for AlumniMentorshipFundLedger.
 * Alumni scholarship endowment fund ledger, scholarship distributions, and student mentorship sessions.
 */
public class AlumniMentorshipFundLedgerService {

    private static final Logger LOGGER = Logger.getLogger(AlumniMentorshipFundLedgerService.class.getName());
    private final Map<String, AlumniMentorshipFundLedgerEntity> store = new ConcurrentHashMap<>();

    public AlumniMentorshipFundLedgerService() {
        seedTelemetry();
    }

    private void seedTelemetry() {
        for (int i = 1; i <= 6; i++) {
            String entityId = "BUF-" + i;
            AlumniMentorshipFundLedgerEntity record = new AlumniMentorshipFundLedgerEntity(
                entityId,
                "REF-BUF-" + (4000 + i),
                "STU-BUF-" + (300 + i),
                "OFF-BUF-" + (80 + i),
                "2026-2027",
                "ENTERPRISE_GRADE_BUFFER",
                "VERIFIED_ACTIVE",
                93.4 + (i * 1.1),
                96.0,
                3200.0 * i,
                "Verified institutional record from Santhamaguluru block inspection",
                "ADMIN_SYSTEM"
            );
            store.put(entityId, record);
        }
    }

    public AlumniMentorshipFundLedgerEntity executeWorkflow(AlumniMentorshipFundLedgerRequestDto dto) {
        if (dto == null || !dto.validatePayload()) {
            LOGGER.log(Level.WARNING, "Rejected execution for AlumniMentorshipFundLedger");
            throw new IllegalArgumentException("Invalid payload provided for AlumniMentorshipFundLedger");
        }

        LOGGER.info("Processing AlumniMentorshipFundLedger workflow for: " + dto.getStudentId());

        AlumniMentorshipFundLedgerEntity entity = new AlumniMentorshipFundLedgerEntity();
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

    public Optional<AlumniMentorshipFundLedgerEntity> getById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(store.get(id));
    }

    public List<AlumniMentorshipFundLedgerEntity> getAll() {
        return new ArrayList<>(store.values());
    }

    public List<AlumniMentorshipFundLedgerEntity> filterByStudent(String studentId) {
        if (studentId == null) return Collections.emptyList();
        return store.values().stream()
                .filter(e -> studentId.equalsIgnoreCase(e.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<AlumniMentorshipFundLedgerEntity> filterByStatus(String status) {
        if (status == null) return Collections.emptyList();
        return store.values().stream()
                .filter(e -> status.equalsIgnoreCase(e.getExecutionStatus()))
                .collect(Collectors.toList());
    }

    public boolean updateExecutionStatus(String id, String newStatus, String notes) {
        AlumniMentorshipFundLedgerEntity entity = store.get(id);
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
                .mapToDouble(AlumniMentorshipFundLedgerEntity::getFinancialImpact)
                .sum();
    }

    public double averageConfidence() {
        return store.values().stream()
                .filter(e -> e.getConfidenceScore() != null)
                .mapToDouble(AlumniMentorshipFundLedgerEntity::getConfidenceScore)
                .average()
                .orElse(0.0);
    }

    public long activeRecordsCount() {
        return store.values().stream().filter(AlumniMentorshipFundLedgerEntity::isActive).count();
    }
}
