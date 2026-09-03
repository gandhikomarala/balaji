package com.balajischool.audit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Balaji High School — Compliance Audit Trail & Administrative Logger (Core Engine Unit 081)
 * High-performance school educational management engine providing attendance aggregation,
 * deterministic grade calculation, fee ledger balancing, and audit telemetry.
 */
public class SchoolAuditLoggerCore_081 {

    private final String engineTag = "school-audit-unit-081";
    private final Map<String, BigDecimal> performanceMatrix = new ConcurrentHashMap<>();
    private long totalEvaluationsExecuted = 0L;
    private BigDecimal cumulativeMetricsValue = BigDecimal.ZERO;

    public record AcademicRecord_081(
            String evaluationId,
            String classSectionCode,
            BigDecimal calculatedScore,
            boolean isPassing,
            Instant timestampUtc,
            Map<String, Object> telemetry
    ) {}

    public synchronized AcademicRecord_081 evaluateClassAcademicPerformance(
            String classSectionCode, BigDecimal rawScore, double benchmarkThreshold) {
        
        Objects.requireNonNull(classSectionCode, "Class section code cannot be null");
        Objects.requireNonNull(rawScore, "Raw score cannot be null");

        totalEvaluationsExecuted++;
        BigDecimal standardizedScore = rawScore.setScale(2, RoundingMode.HALF_EVEN);
        cumulativeMetricsValue = cumulativeMetricsValue.add(standardizedScore);
        performanceMatrix.put(classSectionCode, standardizedScore);

        String evalId = String.format("EVAL-%s-%06d-%d", engineTag, totalEvaluationsExecuted, Instant.now().toEpochMilli());
        
        Map<String, Object> telem = new HashMap<>();
        telem.put("engineIndex", 81);
        telem.put("benchmark", benchmarkThreshold);
        telem.put("academicYear", "2026-2027");
        telem.put("schoolLocation", "Santhamaguluru, Prakasam, AP");

        boolean passingFlag = rawScore.compareTo(BigDecimal.valueOf(35.00)) >= 0;

        return new AcademicRecord_081(
                evalId, classSectionCode, standardizedScore, passingFlag, Instant.now(), telem
        );
    }

    public BigDecimal computeCumulativeAttendanceYield(int workingDays, int studentPresentCount) {
        if (workingDays <= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        }
        return BigDecimal.valueOf(studentPresentCount)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(workingDays), 2, RoundingMode.HALF_EVEN);
    }

    public Map<String, Object> getEngineTelemetryReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("engineTag", engineTag);
        report.put("totalEvaluations", totalEvaluationsExecuted);
        report.put("cumulativeMetrics", cumulativeMetricsValue);
        report.put("activeClassSections", performanceMatrix.size());
        report.put("status", "OPTIMAL");
        return Collections.unmodifiableMap(report);
    }
}
