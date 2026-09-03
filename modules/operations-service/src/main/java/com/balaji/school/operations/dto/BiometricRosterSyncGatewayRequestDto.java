package com.balaji.school.operations.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for BiometricRosterSyncGateway execution.
 */
public class BiometricRosterSyncGatewayRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String studentId;
    private String employeeId;
    private String academicSession;
    private String departmentalCode;
    private Double primaryPerformanceScore;
    private Double secondaryPerformanceScore;
    private Double financialImpactAmount;
    private Double benchmarkThreshold;
    private String contextualNotes;
    private String authorizedOfficer;
    private String verificationTerminalId;
    private LocalDateTime targetExecutionDate;

    public BiometricRosterSyncGatewayRequestDto() {
        this.academicSession = "2026-2027";
        this.targetExecutionDate = LocalDateTime.now();
    }

    public BiometricRosterSyncGatewayRequestDto(String studentId, String employeeId, String academicSession,
                                  String departmentalCode, Double primaryPerformanceScore,
                                  Double secondaryPerformanceScore, Double financialImpactAmount,
                                  Double benchmarkThreshold, String contextualNotes,
                                  String authorizedOfficer, String verificationTerminalId) {
        this.studentId = studentId;
        this.employeeId = employeeId;
        this.academicSession = academicSession != null ? academicSession : "2026-2027";
        this.departmentalCode = departmentalCode;
        this.primaryPerformanceScore = primaryPerformanceScore;
        this.secondaryPerformanceScore = secondaryPerformanceScore;
        this.financialImpactAmount = financialImpactAmount;
        this.benchmarkThreshold = benchmarkThreshold;
        this.contextualNotes = contextualNotes;
        this.authorizedOfficer = authorizedOfficer;
        this.verificationTerminalId = verificationTerminalId;
        this.targetExecutionDate = LocalDateTime.now();
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getAcademicSession() { return academicSession; }
    public void setAcademicSession(String academicSession) { this.academicSession = academicSession; }

    public String getDepartmentalCode() { return departmentalCode; }
    public void setDepartmentalCode(String departmentalCode) { this.departmentalCode = departmentalCode; }

    public Double getPrimaryPerformanceScore() { return primaryPerformanceScore; }
    public void setPrimaryPerformanceScore(Double primaryPerformanceScore) { this.primaryPerformanceScore = primaryPerformanceScore; }

    public Double getSecondaryPerformanceScore() { return secondaryPerformanceScore; }
    public void setSecondaryPerformanceScore(Double secondaryPerformanceScore) { this.secondaryPerformanceScore = secondaryPerformanceScore; }

    public Double getFinancialImpactAmount() { return financialImpactAmount; }
    public void setFinancialImpactAmount(Double financialImpactAmount) { this.financialImpactAmount = financialImpactAmount; }

    public Double getBenchmarkThreshold() { return benchmarkThreshold; }
    public void setBenchmarkThreshold(Double benchmarkThreshold) { this.benchmarkThreshold = benchmarkThreshold; }

    public String getContextualNotes() { return contextualNotes; }
    public void setContextualNotes(String contextualNotes) { this.contextualNotes = contextualNotes; }

    public String getAuthorizedOfficer() { return authorizedOfficer; }
    public void setAuthorizedOfficer(String authorizedOfficer) { this.authorizedOfficer = authorizedOfficer; }

    public String getVerificationTerminalId() { return verificationTerminalId; }
    public void setVerificationTerminalId(String verificationTerminalId) { this.verificationTerminalId = verificationTerminalId; }

    public LocalDateTime getTargetExecutionDate() { return targetExecutionDate; }
    public void setTargetExecutionDate(LocalDateTime targetExecutionDate) { this.targetExecutionDate = targetExecutionDate; }

    public boolean isPayloadValid() {
        return (studentId != null && !studentId.trim().isEmpty()) ||
               (employeeId != null && !employeeId.trim().isEmpty()) ||
               (departmentalCode != null && !departmentalCode.trim().isEmpty());
    }
}
