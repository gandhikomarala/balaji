package com.balaji.school.analytics.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for ChronicAbsenteeismAlertEngine execution.
 */
public class ChronicAbsenteeismAlertEngineRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String studentId;
    private String officerId;
    private String academicYear;
    private String classificationKey;
    private Double confidenceScore;
    private Double secondaryScore;
    private Double financialImpact;
    private String notes;
    private String operatorId;
    private LocalDateTime requestedDate;

    public ChronicAbsenteeismAlertEngineRequestDto() {
        this.academicYear = "2026-2027";
        this.requestedDate = LocalDateTime.now();
    }

    public ChronicAbsenteeismAlertEngineRequestDto(String studentId, String officerId, String academicYear,
                                  String classificationKey, Double confidenceScore, Double secondaryScore,
                                  Double financialImpact, String notes, String operatorId) {
        this.studentId = studentId;
        this.officerId = officerId;
        this.academicYear = academicYear != null ? academicYear : "2026-2027";
        this.classificationKey = classificationKey;
        this.confidenceScore = confidenceScore;
        this.secondaryScore = secondaryScore;
        this.financialImpact = financialImpact;
        this.notes = notes;
        this.operatorId = operatorId;
        this.requestedDate = LocalDateTime.now();
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getOfficerId() { return officerId; }
    public void setOfficerId(String officerId) { this.officerId = officerId; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public String getClassificationKey() { return classificationKey; }
    public void setClassificationKey(String classificationKey) { this.classificationKey = classificationKey; }

    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }

    public Double getSecondaryScore() { return secondaryScore; }
    public void setSecondaryScore(Double secondaryScore) { this.secondaryScore = secondaryScore; }

    public Double getFinancialImpact() { return financialImpact; }
    public void setFinancialImpact(Double financialImpact) { this.financialImpact = financialImpact; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getOperatorId() { return operatorId; }
    public void setOperatorId(String operatorId) { this.operatorId = operatorId; }

    public LocalDateTime getRequestedDate() { return requestedDate; }
    public void setRequestedDate(LocalDateTime requestedDate) { this.requestedDate = requestedDate; }

    public boolean validatePayload() {
        return (studentId != null && !studentId.trim().isEmpty()) ||
               (officerId != null && !officerId.trim().isEmpty()) ||
               (classificationKey != null && !classificationKey.trim().isEmpty());
    }
}
