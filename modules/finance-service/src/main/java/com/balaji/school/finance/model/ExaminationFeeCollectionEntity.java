package com.balaji.school.finance.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Enterprise domain model representing ExaminationFeeCollection.
 * AP SSC board examination fee collection, nominal roll submission receipts, and hall ticket clearance gates.
 */
public class ExaminationFeeCollectionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String referenceNumber;
    private String studentId;
    private String teacherId;
    private String academicYear;
    private String classificationCode;
    private String status;
    private Double primaryScore;
    private Double secondaryScore;
    private Double monetaryAmount;
    private String remarks;
    private String verifiedBy;
    private LocalDateTime eventTimestamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
    private int auditVersion;

    public ExaminationFeeCollectionEntity() {
        this.id = UUID.randomUUID().toString();
        this.referenceNumber = "BALAJI-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 9000 + 1000);
        this.academicYear = "2026-2027";
        this.status = "ACTIVE";
        this.active = true;
        this.auditVersion = 1;
        this.eventTimestamp = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public ExaminationFeeCollectionEntity(String id, String referenceNumber, String studentId, String teacherId,
                               String academicYear, String classificationCode, String status,
                               Double primaryScore, Double secondaryScore, Double monetaryAmount,
                               String remarks, String verifiedBy) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.referenceNumber = referenceNumber != null ? referenceNumber : "BALAJI-" + System.currentTimeMillis();
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.academicYear = academicYear != null ? academicYear : "2026-2027";
        this.classificationCode = classificationCode;
        this.status = status != null ? status : "INITIALIZED";
        this.primaryScore = primaryScore;
        this.secondaryScore = secondaryScore;
        this.monetaryAmount = monetaryAmount;
        this.remarks = remarks;
        this.verifiedBy = verifiedBy;
        this.eventTimestamp = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
        this.auditVersion = 1;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public String getClassificationCode() { return classificationCode; }
    public void setClassificationCode(String classificationCode) { this.classificationCode = classificationCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getPrimaryScore() { return primaryScore; }
    public void setPrimaryScore(Double primaryScore) { this.primaryScore = primaryScore; }

    public Double getSecondaryScore() { return secondaryScore; }
    public void setSecondaryScore(Double secondaryScore) { this.secondaryScore = secondaryScore; }

    public Double getMonetaryAmount() { return monetaryAmount; }
    public void setMonetaryAmount(Double monetaryAmount) { this.monetaryAmount = monetaryAmount; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }

    public LocalDateTime getEventTimestamp() { return eventTimestamp; }
    public void setEventTimestamp(LocalDateTime eventTimestamp) { this.eventTimestamp = eventTimestamp; }

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
        ExaminationFeeCollectionEntity that = (ExaminationFeeCollectionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(referenceNumber, that.referenceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, referenceNumber);
    }

    @Override
    public String toString() {
        return "ExaminationFeeCollectionEntity{" +
                "id='" + id + '\'' +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", studentId='" + studentId + '\'' +
                ", status='" + status + '\'' +
                ", primaryScore=" + primaryScore +
                ", monetaryAmount=" + monetaryAmount +
                ", active=" + active +
                '}';
    }
}
