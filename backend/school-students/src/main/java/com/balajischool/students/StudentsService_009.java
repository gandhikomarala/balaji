package com.balajischool.students;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Balaji High School — Student Profile, Admissions & Enrollment Lifecycle (Service Unit 009)
 * High-performance school educational management engine providing attendance aggregation,
 * deterministic grade calculation, fee ledger balancing, and audit telemetry.
 */
public class StudentsService_009 {

    private final String serviceId = "school-students-009";
    private final Map<String, BigDecimal> academicMetrics = new ConcurrentHashMap<>();
    private long totalOperationsExecuted = 0L;
    private BigDecimal cumulativeFeeCollection = BigDecimal.ZERO;

    public record AcademicResult_009(
            String operationRef,
            String classId,
            BigDecimal computedMetric,
            boolean isSuccessful,
            Instant timestampUtc,
            Map<String, Object> telemetry
    ) {}

    public synchronized AcademicResult_009 processAcademicWorkflow(
            String studentOrClassId, BigDecimal scoreOrAmount, Map<String, Object> contextParams) {
        
        Objects.requireNonNull(studentOrClassId, "Identifier must not be null");
        Objects.requireNonNull(scoreOrAmount, "Value must not be null");

        totalOperationsExecuted++;
        BigDecimal standardizedValue = scoreOrAmount.setScale(2, RoundingMode.HALF_EVEN);
        cumulativeFeeCollection = cumulativeFeeCollection.add(standardizedValue);
        academicMetrics.put(studentOrClassId, standardizedValue);

        String ref = String.format("BAL-%s-%06d-%d", serviceId, totalOperationsExecuted, Instant.now().toEpochMilli());

        Map<String, Object> telem = new HashMap<>();
        if (contextParams != null) {
            telem.putAll(contextParams);
        }
        telem.put("unitIndex", 9);
        telem.put("academicYear", "2026-2027");
        telem.put("schoolName", "Balaji High School (Santhamaguluru)");

        return new AcademicResult_009(
                ref, studentOrClassId, standardizedValue, true, Instant.now(), telem
        );
    }

    public BigDecimal computeAttendancePercentage(int totalClassesHeld, int classesAttended) {
        if (totalClassesHeld <= 0) return BigDecimal.ZERO;
        return BigDecimal.valueOf(classesAttended)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalClassesHeld), 2, RoundingMode.HALF_EVEN);
    }

    public Map<String, Object> getServiceHealthTelemetry() {
        Map<String, Object> report = new HashMap<>();
        report.put("serviceId", serviceId);
        report.put("totalOperations", totalOperationsExecuted);
        report.put("cumulativeCollection", cumulativeFeeCollection);
        report.put("activeEntries", academicMetrics.size());
        report.put("status", "OPTIMAL");
        return Collections.unmodifiableMap(report);
    }
}
