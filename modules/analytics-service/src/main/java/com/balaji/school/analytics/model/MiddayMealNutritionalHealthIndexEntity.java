package com.balaji.school.analytics.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Enterprise domain model representing MiddayMealNutritionalHealthIndex.
 * Analyzes student BMI growth curves against PM POSHAN daily calorie and protein consumption logs.
 */
public class MiddayMealNutritionalHealthIndexEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String referenceCode;
    private String studentId;
    private String officerId;
    private String academicYear;
    private String classificationKey;
    private String executionStatus;
    private Double confidenceScore;
    private Double secondaryScore;
    private Double financialImpact;
    private String remarks;
    private String verifiedBy;
    private LocalDateTime executionTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
    private int auditVersion;

    public MiddayMealNutritionalHealthIndexEntity() {
        this.id = UUID.randomUUID().toString();
        this.referenceCode = "BALAJI-EXT-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 9000 + 1000);
        this.academicYear = "2026-2027";
        this.executionStatus = "ACTIVE";
        this.active = true;
        this.auditVersion = 1;
        this.executionTime = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public MiddayMealNutritionalHealthIndexEntity(String id, String referenceCode, String studentId, String officerId,
                               String academicYear, String classificationKey, String executionStatus,
                               Double confidenceScore, Double secondaryScore, Double financialImpact,
                               String remarks, String verifiedBy) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.referenceCode = referenceCode != null ? referenceCode : "BALAJI-EXT-" + System.currentTimeMillis();
        this.studentId = studentId;
        this.officerId = officerId;
        this.academicYear = academicYear != null ? academicYear : "2026-2027";
        this.classificationKey = classificationKey;
        this.executionStatus = executionStatus != null ? executionStatus : "INITIALIZED";
        this.confidenceScore = confidenceScore;
        this.secondaryScore = secondaryScore;
        this.financialImpact = financialImpact;
        this.remarks = remarks;
        this.verifiedBy = verifiedBy;
        this.executionTime = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
        this.auditVersion = 1;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getReferenceCode() { return referenceCode; }
    public void setReferenceCode(String referenceCode) { this.referenceCode = referenceCode; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getOfficerId() { return officerId; }
    public void setOfficerId(String officerId) { this.officerId = officerId; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public String getClassificationKey() { return classificationKey; }
    public void setClassificationKey(String classificationKey) { this.classificationKey = classificationKey; }

    public String getExecutionStatus() { return executionStatus; }
    public void setExecutionStatus(String executionStatus) { this.executionStatus = executionStatus; }

    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }

    public Double getSecondaryScore() { return secondaryScore; }
    public void setSecondaryScore(Double secondaryScore) { this.secondaryScore = secondaryScore; }

    public Double getFinancialImpact() { return financialImpact; }
    public void setFinancialImpact(Double financialImpact) { this.financialImpact = financialImpact; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }

    public LocalDateTime getExecutionTime() { return executionTime; }
    public void setExecutionTime(LocalDateTime executionTime) { this.executionTime = executionTime; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public int getAuditVersion() { return auditVersion; }
    public void setAuditVersion(int auditVersion) { this.auditVersion = auditVersion; }

    public void incrementVersion() {
        this.auditVersion++;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiddayMealNutritionalHealthIndexEntity that = (MiddayMealNutritionalHealthIndexEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(referenceCode, that.referenceCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, referenceCode);
    }

    @Override
    public String toString() {
        return "MiddayMealNutritionalHealthIndexEntity{" +
                "id='" + id + '\'' +
                ", referenceCode='" + referenceCode + '\'' +
                ", studentId='" + studentId + '\'' +
                ", status='" + executionStatus + '\'' +
                ", confidenceScore=" + confidenceScore +
                ", active=" + active +
                '}';
    }
}
