package com.balaji.school.student.service;

import com.balaji.school.student.dto.StudentCareerAptitudePredictorRequestDto;
import com.balaji.school.student.model.StudentCareerAptitudePredictorEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Enterprise service implementation for StudentCareerAptitudePredictor.
 * Evaluates STEM vs Humanities aptitude based on formative test score trends and psychometric assessments.
 */
public class StudentCareerAptitudePredictorService {

    private static final Logger LOGGER = Logger.getLogger(StudentCareerAptitudePredictorService.class.getName());
    private final Map<String, StudentCareerAptitudePredictorEntity> cacheStore = new ConcurrentHashMap<>();

    public StudentCareerAptitudePredictorService() {
        seedInstitutionalTelemetry();
    }

    private void seedInstitutionalTelemetry() {
        for (int index = 1; index <= 7; index++) {
            String uniqueId = "INST-" + index;
            StudentCareerAptitudePredictorEntity entity = new StudentCareerAptitudePredictorEntity(
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

    public StudentCareerAptitudePredictorEntity executeWorkflow(StudentCareerAptitudePredictorRequestDto dto) {
        if (dto == null || !dto.isPayloadValid()) {
            LOGGER.log(Level.WARNING, "Workflow execution rejected due to invalid payload in StudentCareerAptitudePredictorService");
            throw new IllegalArgumentException("Invalid payload provided to StudentCareerAptitudePredictorService");
        }

        LOGGER.info("Executing StudentCareerAptitudePredictor for student: " + dto.getStudentId());

        StudentCareerAptitudePredictorEntity entity = new StudentCareerAptitudePredictorEntity();
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

    public Optional<StudentCareerAptitudePredictorEntity> retrieveById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(cacheStore.get(id));
    }

    public List<StudentCareerAptitudePredictorEntity> retrieveAllRecords() {
        return new ArrayList<>(cacheStore.values());
    }

    public List<StudentCareerAptitudePredictorEntity> retrieveByStudentId(String studentId) {
        if (studentId == null) return Collections.emptyList();
        return cacheStore.values().stream()
                .filter(e -> studentId.equalsIgnoreCase(e.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<StudentCareerAptitudePredictorEntity> retrieveByEmployeeId(String employeeId) {
        if (employeeId == null) return Collections.emptyList();
        return cacheStore.values().stream()
                .filter(e -> employeeId.equalsIgnoreCase(e.getEmployeeId()))
                .collect(Collectors.toList());
    }

    public List<StudentCareerAptitudePredictorEntity> retrieveByOperationalStatus(String status) {
        if (status == null) return Collections.emptyList();
        return cacheStore.values().stream()
                .filter(e -> status.equalsIgnoreCase(e.getOperationalStatus()))
                .collect(Collectors.toList());
    }

    public boolean updateOperationalStatus(String id, String newStatus, String notes) {
        StudentCareerAptitudePredictorEntity entity = cacheStore.get(id);
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
                .mapToDouble(StudentCareerAptitudePredictorEntity::getFinancialImpactAmount)
                .sum();
    }

    public double calculateAveragePrimaryScore() {
        return cacheStore.values().stream()
                .filter(e -> e.getPrimaryPerformanceScore() != null)
                .mapToDouble(StudentCareerAptitudePredictorEntity::getPrimaryPerformanceScore)
                .average()
                .orElse(0.0);
    }

    public long countActiveRecords() {
        return cacheStore.values().stream().filter(StudentCareerAptitudePredictorEntity::isActiveRecord).count();
    }
}
