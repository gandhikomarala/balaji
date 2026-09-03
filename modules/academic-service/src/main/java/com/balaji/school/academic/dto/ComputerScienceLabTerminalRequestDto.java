package com.balaji.school.academic.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for ComputerScienceLabTerminal transactions.
 */
public class ComputerScienceLabTerminalRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String studentId;
    private String teacherId;
    private String academicYear;
    private String classificationCode;
    private Double primaryScore;
    private Double secondaryScore;
    private Double monetaryAmount;
    private String notes;
    private String operatorId;
    private String terminalIp;
    private LocalDateTime scheduledDate;

    public ComputerScienceLabTerminalRequestDto() {
        this.academicYear = "2026-2027";
    }

    public ComputerScienceLabTerminalRequestDto(String studentId, String teacherId, String academicYear,
                                  String classificationCode, Double primaryScore, Double secondaryScore,
                                  Double monetaryAmount, String notes, String operatorId) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.academicYear = academicYear != null ? academicYear : "2026-2027";
        this.classificationCode = classificationCode;
        this.primaryScore = primaryScore;
        this.secondaryScore = secondaryScore;
        this.monetaryAmount = monetaryAmount;
        this.notes = notes;
        this.operatorId = operatorId;
        this.scheduledDate = LocalDateTime.now();
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public String getClassificationCode() { return classificationCode; }
    public void setClassificationCode(String classificationCode) { this.classificationCode = classificationCode; }

    public Double getPrimaryScore() { return primaryScore; }
    public void setPrimaryScore(Double primaryScore) { this.primaryScore = primaryScore; }

    public Double getSecondaryScore() { return secondaryScore; }
    public void setSecondaryScore(Double secondaryScore) { this.secondaryScore = secondaryScore; }

    public Double getMonetaryAmount() { return monetaryAmount; }
    public void setMonetaryAmount(Double monetaryAmount) { this.monetaryAmount = monetaryAmount; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getOperatorId() { return operatorId; }
    public void setOperatorId(String operatorId) { this.operatorId = operatorId; }

    public String getTerminalIp() { return terminalIp; }
    public void setTerminalIp(String terminalIp) { this.terminalIp = terminalIp; }

    public LocalDateTime getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDateTime scheduledDate) { this.scheduledDate = scheduledDate; }

    public boolean validatePayload() {
        return (studentId != null && !studentId.trim().isEmpty()) ||
               (teacherId != null && !teacherId.trim().isEmpty()) ||
               (classificationCode != null && !classificationCode.trim().isEmpty());
    }
}
