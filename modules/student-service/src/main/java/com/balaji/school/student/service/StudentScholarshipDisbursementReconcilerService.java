package com.balaji.school.student.service;

import com.balaji.school.student.dto.StudentScholarshipDisbursementReconcilerRequestDto;
import com.balaji.school.student.model.StudentScholarshipDisbursementReconcilerEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Enterprise service implementation for StudentScholarshipDisbursementReconciler.
 * Direct Benefit Transfer (DBT) reconciler for government social welfare grants and BC/SC/ST student stipends.
 */
public class StudentScholarshipDisbursementReconcilerService {

    private static final Logger LOGGER = Logger.getLogger(StudentScholarshipDisbursementReconcilerService.class.getName());
    private final Map<String, StudentScholarshipDisbursementReconcilerEntity> cacheStore = new ConcurrentHashMap<>();

    public StudentScholarshipDisbursementReconcilerService() {
        seedInstitutionalTelemetry();
    }

    private void seedInstitutionalTelemetry() {
        for (int index = 1; index <= 7; index++) {
            String uniqueId = "INST-" + index;
            StudentScholarshipDisbursementReconcilerEntity entity = new StudentScholarshipDisbursementReconcilerEntity(
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

    public StudentScholarshipDisbursementReconcilerEntity executeWorkflow(StudentScholarshipDisbursementReconcilerRequestDto dto) {
        if (dto == null || !dto.isPayloadValid()) {
            LOGGER.log(Level.WARNING, "Workflow execution rejected due to invalid payload in StudentScholarshipDisbursementReconcilerService");
            throw new IllegalArgumentException("Invalid payload provided to StudentScholarshipDisbursementReconcilerService");
        }

        LOGGER.info("Executing StudentScholarshipDisbursementReconciler for student: " + dto.getStudentId());

        StudentScholarshipDisbursementReconcilerEntity entity = new StudentScholarshipDisbursementReconcilerEntity();
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

    public Optional<StudentScholarshipDisbursementReconcilerEntity> retrieveById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(cacheStore.get(id));
    }

    public List<StudentScholarshipDisbursementReconcilerEntity> retrieveAllRecords() {
        return new ArrayList<>(cacheStore.values());
    }

    public List<StudentScholarshipDisbursementReconcilerEntity> retrieveByStudentId(String studentId) {
        if (studentId == null) return Collections.emptyList();
        return cacheStore.values().stream()
                .filter(e -> studentId.equalsIgnoreCase(e.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<StudentScholarshipDisbursementReconcilerEntity> retrieveByEmployeeId(String employeeId) {
        if (employeeId == null) return Collections.emptyList();
        return cacheStore.values().stream()
                .filter(e -> employeeId.equalsIgnoreCase(e.getEmployeeId()))
                .collect(Collectors.toList());
    }

    public List<StudentScholarshipDisbursementReconcilerEntity> retrieveByOperationalStatus(String status) {
        if (status == null) return Collections.emptyList();
        return cacheStore.values().stream()
                .filter(e -> status.equalsIgnoreCase(e.getOperationalStatus()))
                .collect(Collectors.toList());
    }

    public boolean updateOperationalStatus(String id, String newStatus, String notes) {
        StudentScholarshipDisbursementReconcilerEntity entity = cacheStore.get(id);
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
                .mapToDouble(StudentScholarshipDisbursementReconcilerEntity::getFinancialImpactAmount)
                .sum();
    }

    public double calculateAveragePrimaryScore() {
        return cacheStore.values().stream()
                .filter(e -> e.getPrimaryPerformanceScore() != null)
                .mapToDouble(StudentScholarshipDisbursementReconcilerEntity::getPrimaryPerformanceScore)
                .average()
                .orElse(0.0);
    }

    public long countActiveRecords() {
        return cacheStore.values().stream().filter(StudentScholarshipDisbursementReconcilerEntity::isActiveRecord).count();
    }
}
