package com.balaji.school.student.service;

import com.balaji.school.student.dto.StudentMiddayMealNutritionAuditorRequestDto;
import com.balaji.school.student.model.StudentMiddayMealNutritionAuditorEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Enterprise service implementation for StudentMiddayMealNutritionAuditor.
 * Tracks daily PM POSHAN rice/dal rations, egg distribution, and menu nutrition standards.
 */
public class StudentMiddayMealNutritionAuditorService {

    private static final Logger LOGGER = Logger.getLogger(StudentMiddayMealNutritionAuditorService.class.getName());
    private final Map<String, StudentMiddayMealNutritionAuditorEntity> cacheStore = new ConcurrentHashMap<>();

    public StudentMiddayMealNutritionAuditorService() {
        seedInstitutionalTelemetry();
    }

    private void seedInstitutionalTelemetry() {
        for (int index = 1; index <= 7; index++) {
            String uniqueId = "INST-" + index;
            StudentMiddayMealNutritionAuditorEntity entity = new StudentMiddayMealNutritionAuditorEntity(
                uniqueId,
                "BALAJI-INST-2026-" + (5000 + index),
                "STU-SANTH-" + (1000 + index),
                "EMP-BALAJI-" + (100 + index),
                "2026-2027",
                "DEPT_AP_SSC_BOARD",
                "VERIFIED_ACTIVE",
                92.5 + (index * 0.8),
                96.2,
                4500.0 * index,
                85.0,
                "Verified institutional record compliant with Andhra Pradesh SCERT directives",
                "OFFICER_IN_CHARGE",
                "TERMINAL_GATE_0" + index
            );
            cacheStore.put(uniqueId, entity);
        }
    }

    public StudentMiddayMealNutritionAuditorEntity executeWorkflow(StudentMiddayMealNutritionAuditorRequestDto dto) {
        if (dto == null || !dto.isPayloadValid()) {
            LOGGER.log(Level.WARNING, "Workflow execution rejected due to invalid payload in StudentMiddayMealNutritionAuditorService");
            throw new IllegalArgumentException("Invalid payload provided to StudentMiddayMealNutritionAuditorService");
        }

        LOGGER.info("Executing StudentMiddayMealNutritionAuditor for student: " + dto.getStudentId());

        StudentMiddayMealNutritionAuditorEntity entity = new StudentMiddayMealNutritionAuditorEntity();
        entity.setStudentId(dto.getStudentId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setAcademicSession(dto.getAcademicSession());
        entity.setDepartmentalCode(dto.getDepartmentalCode());
        entity.setPrimaryPerformanceScore(dto.getPrimaryPerformanceScore());
        entity.setSecondaryPerformanceScore(dto.getSecondaryPerformanceScore());
        entity.setFinancialImpactAmount(dto.getFinancialImpactAmount());
        entity.setBenchmarkThreshold(dto.getBenchmarkThreshold());
        entity.setContextualNotes(dto.getContextualNotes());
        entity.setAuthorizedOfficer(dto.getAuthorizedOfficer());
        entity.setVerificationTerminalId(dto.getVerificationTerminalId());
        entity.setOperationalStatus("COMPLETED_VERIFIED");
        entity.setRecordUpdatedTimestamp(LocalDateTime.now());

        cacheStore.put(entity.getId(), entity);
        return entity;
    }

    public Optional<StudentMiddayMealNutritionAuditorEntity> retrieveById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(cacheStore.get(id));
    }

    public List<StudentMiddayMealNutritionAuditorEntity> retrieveAllRecords() {
        return new ArrayList<>(cacheStore.values());
    }

    public List<StudentMiddayMealNutritionAuditorEntity> retrieveByStudentId(String studentId) {
        if (studentId == null) return Collections.emptyList();
        return cacheStore.values().stream()
                .filter(e -> studentId.equalsIgnoreCase(e.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<StudentMiddayMealNutritionAuditorEntity> retrieveByEmployeeId(String employeeId) {
        if (employeeId == null) return Collections.emptyList();
        return cacheStore.values().stream()
                .filter(e -> employeeId.equalsIgnoreCase(e.getEmployeeId()))
                .collect(Collectors.toList());
    }

    public List<StudentMiddayMealNutritionAuditorEntity> retrieveByOperationalStatus(String status) {
        if (status == null) return Collections.emptyList();
        return cacheStore.values().stream()
                .filter(e -> status.equalsIgnoreCase(e.getOperationalStatus()))
                .collect(Collectors.toList());
    }

    public boolean updateOperationalStatus(String id, String newStatus, String notes) {
        StudentMiddayMealNutritionAuditorEntity entity = cacheStore.get(id);
        if (entity != null) {
            entity.setOperationalStatus(newStatus);
            entity.setContextualNotes(notes + " [Audited: " + LocalDateTime.now() + "]");
            entity.incrementAuditRevision();
            return true;
        }
        return false;
    }

    public boolean removeRecordById(String id) {
        return cacheStore.remove(id) != null;
    }

    public double calculateTotalFinancialImpact() {
        return cacheStore.values().stream()
                .filter(e -> e.getFinancialImpactAmount() != null)
                .mapToDouble(StudentMiddayMealNutritionAuditorEntity::getFinancialImpactAmount)
                .sum();
    }

    public double calculateAveragePrimaryScore() {
        return cacheStore.values().stream()
                .filter(e -> e.getPrimaryPerformanceScore() != null)
                .mapToDouble(StudentMiddayMealNutritionAuditorEntity::getPrimaryPerformanceScore)
                .average()
                .orElse(0.0);
    }

    public long countActiveRecords() {
        return cacheStore.values().stream().filter(StudentMiddayMealNutritionAuditorEntity::isActiveRecord).count();
    }
}
