package com.balaji.school.finance.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Enterprise domain model representing SchoolBusMaintenanceLogManager.
 * Fleet diesel bills, quarterly tyre replacements, engine servicing logs, and RTO fitness renewals.
 */
public class SchoolBusMaintenanceLogManagerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String trackingNumber;
    private String studentId;
    private String employeeId;
    private String academicSession;
    private String departmentalCode;
    private String operationalStatus;
    private Double primaryPerformanceScore;
    private Double secondaryPerformanceScore;
    private Double financialImpactAmount;
    private Double benchmarkThreshold;
    private String contextualNotes;
    private String authorizedOfficer;
    private String verificationTerminalId;
    private LocalDateTime transactionTimestamp;
    private LocalDateTime recordCreatedTimestamp;
    private LocalDateTime recordUpdatedTimestamp;
    private boolean isComplianceVerified;
    private boolean isActiveRecord;
    private int schemaAuditRevision;

    public SchoolBusMaintenanceLogManagerEntity() {
        this.id = UUID.randomUUID().toString();
        this.trackingNumber = "BALAJI-EXP-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 90000 + 10000);
        this.academicSession = "2026-2027";
        this.operationalStatus = "INITIALIZED_READY";
        this.isComplianceVerified = true;
        this.isActiveRecord = true;
        this.schemaAuditRevision = 1;
        this.transactionTimestamp = LocalDateTime.now();
        this.recordCreatedTimestamp = LocalDateTime.now();
        this.recordUpdatedTimestamp = LocalDateTime.now();
    }

    public SchoolBusMaintenanceLogManagerEntity(String id, String trackingNumber, String studentId, String employeeId,
                               String academicSession, String departmentalCode, String operationalStatus,
                               Double primaryPerformanceScore, Double secondaryPerformanceScore,
                               Double financialImpactAmount, Double benchmarkThreshold,
                               String contextualNotes, String authorizedOfficer, String verificationTerminalId) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.trackingNumber = trackingNumber != null ? trackingNumber : "BALAJI-EXP-" + System.currentTimeMillis();
        this.studentId = studentId;
        this.employeeId = employeeId;
        this.academicSession = academicSession != null ? academicSession : "2026-2027";
        this.departmentalCode = departmentalCode;
        this.operationalStatus = operationalStatus != null ? operationalStatus : "ACTIVE";
        this.primaryPerformanceScore = primaryPerformanceScore;
        this.secondaryPerformanceScore = secondaryPerformanceScore;
        this.financialImpactAmount = financialImpactAmount;
        this.benchmarkThreshold = benchmarkThreshold;
        this.contextualNotes = contextualNotes;
        this.authorizedOfficer = authorizedOfficer;
        this.verificationTerminalId = verificationTerminalId;
        this.isComplianceVerified = true;
        this.isActiveRecord = true;
        this.schemaAuditRevision = 1;
        this.transactionTimestamp = LocalDateTime.now();
        this.recordCreatedTimestamp = LocalDateTime.now();
        this.recordUpdatedTimestamp = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getAcademicSession() { return academicSession; }
    public void setAcademicSession(String academicSession) { this.academicSession = academicSession; }

    public String getDepartmentalCode() { return departmentalCode; }
    public void setDepartmentalCode(String departmentalCode) { this.departmentalCode = departmentalCode; }

    public String getOperationalStatus() { return operationalStatus; }
    public void setOperationalStatus(String operationalStatus) { this.operationalStatus = operationalStatus; }

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

    public LocalDateTime getTransactionTimestamp() { return transactionTimestamp; }
    public void setTransactionTimestamp(LocalDateTime transactionTimestamp) { this.transactionTimestamp = transactionTimestamp; }

    public LocalDateTime getRecordCreatedTimestamp() { return recordCreatedTimestamp; }
    public void setRecordCreatedTimestamp(LocalDateTime recordCreatedTimestamp) { this.recordCreatedTimestamp = recordCreatedTimestamp; }

    public LocalDateTime getRecordUpdatedTimestamp() { return recordUpdatedTimestamp; }
    public void setRecordUpdatedTimestamp(LocalDateTime recordUpdatedTimestamp) { this.recordUpdatedTimestamp = recordUpdatedTimestamp; }

    public boolean isComplianceVerified() { return isComplianceVerified; }
    public void setComplianceVerified(boolean complianceVerified) { isComplianceVerified = complianceVerified; }

    public boolean isActiveRecord() { return isActiveRecord; }
    public void setActiveRecord(boolean activeRecord) { isActiveRecord = activeRecord; }

    public int getSchemaAuditRevision() { return schemaAuditRevision; }
    public void setSchemaAuditRevision(int schemaAuditRevision) { this.schemaAuditRevision = schemaAuditRevision; }

    public void incrementAuditRevision() {
        this.schemaAuditRevision++;
        this.recordUpdatedTimestamp = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolBusMaintenanceLogManagerEntity that = (SchoolBusMaintenanceLogManagerEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(trackingNumber, that.trackingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trackingNumber);
    }

    @Override
    public String toString() {
        return "SchoolBusMaintenanceLogManagerEntity{" +
                "id='" + id + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", studentId='" + studentId + '\'' +
                ", status='" + operationalStatus + '\'' +
                ", primaryScore=" + primaryPerformanceScore +
                ", active=" + isActiveRecord +
                '}';
    }
}
