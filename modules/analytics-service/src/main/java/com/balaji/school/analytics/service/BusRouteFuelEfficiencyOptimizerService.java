package com.balaji.school.analytics.service;

import com.balaji.school.analytics.dto.BusRouteFuelEfficiencyOptimizerRequestDto;
import com.balaji.school.analytics.model.BusRouteFuelEfficiencyOptimizerEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service orchestrator for BusRouteFuelEfficiencyOptimizer.
 * Analyzes school bus GPS telematics to optimize pickup routes, reduce transit times, and lower diesel costs.
 */
public class BusRouteFuelEfficiencyOptimizerService {

    private static final Logger LOGGER = Logger.getLogger(BusRouteFuelEfficiencyOptimizerService.class.getName());
    private final Map<String, BusRouteFuelEfficiencyOptimizerEntity> store = new ConcurrentHashMap<>();

    public BusRouteFuelEfficiencyOptimizerService() {
        seedTelemetry();
    }

    private void seedTelemetry() {
        for (int i = 1; i <= 6; i++) {
            String entityId = "EXT-" + i;
            BusRouteFuelEfficiencyOptimizerEntity record = new BusRouteFuelEfficiencyOptimizerEntity(
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

    public BusRouteFuelEfficiencyOptimizerEntity executeWorkflow(BusRouteFuelEfficiencyOptimizerRequestDto dto) {
        if (dto == null || !dto.validatePayload()) {
            LOGGER.log(Level.WARNING, "Rejected execution for BusRouteFuelEfficiencyOptimizer");
            throw new IllegalArgumentException("Invalid payload provided for BusRouteFuelEfficiencyOptimizer");
        }

        LOGGER.info("Processing BusRouteFuelEfficiencyOptimizer workflow for: " + dto.getStudentId());

        BusRouteFuelEfficiencyOptimizerEntity entity = new BusRouteFuelEfficiencyOptimizerEntity();
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

    public Optional<BusRouteFuelEfficiencyOptimizerEntity> getById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(store.get(id));
    }

    public List<BusRouteFuelEfficiencyOptimizerEntity> getAll() {
        return new ArrayList<>(store.values());
    }

    public List<BusRouteFuelEfficiencyOptimizerEntity> filterByStudent(String studentId) {
        if (studentId == null) return Collections.emptyList();
        return store.values().stream()
                .filter(e -> studentId.equalsIgnoreCase(e.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<BusRouteFuelEfficiencyOptimizerEntity> filterByStatus(String status) {
        if (status == null) return Collections.emptyList();
        return store.values().stream()
                .filter(e -> status.equalsIgnoreCase(e.getExecutionStatus()))
                .collect(Collectors.toList());
    }

    public boolean updateExecutionStatus(String id, String newStatus, String notes) {
        BusRouteFuelEfficiencyOptimizerEntity entity = store.get(id);
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
                .mapToDouble(BusRouteFuelEfficiencyOptimizerEntity::getFinancialImpact)
                .sum();
    }

    public double averageConfidence() {
        return store.values().stream()
                .filter(e -> e.getConfidenceScore() != null)
                .mapToDouble(BusRouteFuelEfficiencyOptimizerEntity::getConfidenceScore)
                .average()
                .orElse(0.0);
    }

    public long activeRecordsCount() {
        return store.values().stream().filter(BusRouteFuelEfficiencyOptimizerEntity::isActive).count();
    }
}
