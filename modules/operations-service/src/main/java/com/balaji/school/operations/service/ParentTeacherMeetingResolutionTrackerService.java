package com.balaji.school.operations.service;

import com.balaji.school.operations.dto.ParentTeacherMeetingResolutionTrackerRequestDto;
import com.balaji.school.operations.model.ParentTeacherMeetingResolutionTrackerEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service orchestrator for ParentTeacherMeetingResolutionTracker.
 * Logs parent feedback resolutions, special counseling action plans, and teacher follow-ups.
 */
public class ParentTeacherMeetingResolutionTrackerService {

    private static final Logger LOGGER = Logger.getLogger(ParentTeacherMeetingResolutionTrackerService.class.getName());
    private final Map<String, ParentTeacherMeetingResolutionTrackerEntity> store = new ConcurrentHashMap<>();

    public ParentTeacherMeetingResolutionTrackerService() {
        seedTelemetry();
    }

    private void seedTelemetry() {
        for (int i = 1; i <= 6; i++) {
            String entityId = "BUF-" + i;
            ParentTeacherMeetingResolutionTrackerEntity record = new ParentTeacherMeetingResolutionTrackerEntity(
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

    public ParentTeacherMeetingResolutionTrackerEntity executeWorkflow(ParentTeacherMeetingResolutionTrackerRequestDto dto) {
        if (dto == null || !dto.validatePayload()) {
            LOGGER.log(Level.WARNING, "Rejected execution for ParentTeacherMeetingResolutionTracker");
            throw new IllegalArgumentException("Invalid payload provided for ParentTeacherMeetingResolutionTracker");
        }

        LOGGER.info("Processing ParentTeacherMeetingResolutionTracker workflow for: " + dto.getStudentId());

        ParentTeacherMeetingResolutionTrackerEntity entity = new ParentTeacherMeetingResolutionTrackerEntity();
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

    public Optional<ParentTeacherMeetingResolutionTrackerEntity> getById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(store.get(id));
    }

    public List<ParentTeacherMeetingResolutionTrackerEntity> getAll() {
        return new ArrayList<>(store.values());
    }

    public List<ParentTeacherMeetingResolutionTrackerEntity> filterByStudent(String studentId) {
        if (studentId == null) return Collections.emptyList();
        return store.values().stream()
                .filter(e -> studentId.equalsIgnoreCase(e.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<ParentTeacherMeetingResolutionTrackerEntity> filterByStatus(String status) {
        if (status == null) return Collections.emptyList();
        return store.values().stream()
                .filter(e -> status.equalsIgnoreCase(e.getExecutionStatus()))
                .collect(Collectors.toList());
    }

    public boolean updateExecutionStatus(String id, String newStatus, String notes) {
        ParentTeacherMeetingResolutionTrackerEntity entity = store.get(id);
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
                .mapToDouble(ParentTeacherMeetingResolutionTrackerEntity::getFinancialImpact)
                .sum();
    }

    public double averageConfidence() {
        return store.values().stream()
                .filter(e -> e.getConfidenceScore() != null)
                .mapToDouble(ParentTeacherMeetingResolutionTrackerEntity::getConfidenceScore)
                .average()
                .orElse(0.0);
    }

    public long activeRecordsCount() {
        return store.values().stream().filter(ParentTeacherMeetingResolutionTrackerEntity::isActive).count();
    }
}
