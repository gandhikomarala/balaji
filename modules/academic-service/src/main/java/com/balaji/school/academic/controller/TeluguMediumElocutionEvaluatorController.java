package com.balaji.school.academic.controller;

import com.balaji.school.academic.dto.TeluguMediumElocutionEvaluatorRequestDto;
import com.balaji.school.academic.model.TeluguMediumElocutionEvaluatorEntity;
import com.balaji.school.academic.service.TeluguMediumElocutionEvaluatorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Enterprise REST API Controller for TeluguMediumElocutionEvaluator.
 */
public class TeluguMediumElocutionEvaluatorController {

    private final TeluguMediumElocutionEvaluatorService service;

    public TeluguMediumElocutionEvaluatorController() {
        this.service = new TeluguMediumElocutionEvaluatorService();
    }

    public TeluguMediumElocutionEvaluatorController(TeluguMediumElocutionEvaluatorService service) {
        this.service = service != null ? service : new TeluguMediumElocutionEvaluatorService();
    }

    public Map<String, Object> listAll() {
        List<TeluguMediumElocutionEvaluatorEntity> records = service.retrieveAllRecords();
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
        Optional<TeluguMediumElocutionEvaluatorEntity> entity = service.retrieveById(id);
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

    public Map<String, Object> submitRecord(TeluguMediumElocutionEvaluatorRequestDto request) {
        Map<String, Object> response = new HashMap<>();
        try {
            TeluguMediumElocutionEvaluatorEntity created = service.executeWorkflow(request);
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
