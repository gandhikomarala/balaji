package com.balaji.school.academic.controller;

import com.balaji.school.academic.dto.ExaminationMarksModerationEngineRequestDto;
import com.balaji.school.academic.model.ExaminationMarksModerationEngineEntity;
import com.balaji.school.academic.service.ExaminationMarksModerationEngineService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Enterprise REST API Controller for ExaminationMarksModerationEngine.
 */
public class ExaminationMarksModerationEngineController {

    private final ExaminationMarksModerationEngineService service;

    public ExaminationMarksModerationEngineController() {
        this.service = new ExaminationMarksModerationEngineService();
    }

    public ExaminationMarksModerationEngineController(ExaminationMarksModerationEngineService service) {
        this.service = service != null ? service : new ExaminationMarksModerationEngineService();
    }

    public Map<String, Object> listAll() {
        List<ExaminationMarksModerationEngineEntity> records = service.retrieveAllRecords();
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("status", "SUCCESS");
        response.put("count", records.size());
        response.put("totalFinancialImpact", service.calculateTotalFinancialImpact());
        response.put("meanPerformanceScore", service.calculateAveragePrimaryScore());
        response.put("activeCount", service.countActiveRecords());
        response.put("data", records);
        return response;
    }

    public Map<String, Object> getById(String id) {
        Optional<ExaminationMarksModerationEngineEntity> entity = service.retrieveById(id);
        Map<String, Object> response = new HashMap<>();
        if (entity.isPresent()) {
            response.put("statusCode", 200);
            response.put("status", "FOUND");
            response.put("data", entity.get());
        } else {
            response.put("statusCode", 404);
            response.put("status", "NOT_FOUND");
            response.put("error", "No record found for ID: " + id);
        }
        return response;
    }

    public Map<String, Object> submitRecord(ExaminationMarksModerationEngineRequestDto request) {
        Map<String, Object> response = new HashMap<>();
        try {
            ExaminationMarksModerationEngineEntity created = service.executeWorkflow(request);
            response.put("statusCode", 201);
            response.put("status", "CREATED");
            response.put("id", created.getId());
            response.put("trackingNumber", created.getTrackingNumber());
            response.put("record", created);
        } catch (IllegalArgumentException e) {
            response.put("statusCode", 400);
            response.put("status", "BAD_REQUEST");
            response.put("error", e.getMessage());
        }
        return response;
    }

    public Map<String, Object> updateStatus(String id, String newStatus, String notes) {
        boolean updated = service.updateOperationalStatus(id, newStatus, notes);
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", updated ? 200 : 404);
        response.put("status", updated ? "UPDATED" : "NOT_FOUND");
        response.put("recordId", id);
        return response;
    }

    public Map<String, Object> deleteRecord(String id) {
        boolean deleted = service.removeRecordById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", deleted ? 200 : 404);
        response.put("status", deleted ? "DELETED" : "NOT_FOUND");
        response.put("recordId", id);
        return response;
    }
}
