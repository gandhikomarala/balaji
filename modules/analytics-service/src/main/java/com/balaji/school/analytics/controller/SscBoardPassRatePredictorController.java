package com.balaji.school.analytics.controller;

import com.balaji.school.analytics.dto.SscBoardPassRatePredictorRequestDto;
import com.balaji.school.analytics.model.SscBoardPassRatePredictorEntity;
import com.balaji.school.analytics.service.SscBoardPassRatePredictorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST API Endpoint Controller for SscBoardPassRatePredictor.
 */
public class SscBoardPassRatePredictorController {

    private final SscBoardPassRatePredictorService service;

    public SscBoardPassRatePredictorController() {
        this.service = new SscBoardPassRatePredictorService();
    }

    public SscBoardPassRatePredictorController(SscBoardPassRatePredictorService service) {
        this.service = service != null ? service : new SscBoardPassRatePredictorService();
    }

    public Map<String, Object> listAll() {
        List<SscBoardPassRatePredictorEntity> records = service.getAll();
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("status", "SUCCESS");
        response.put("count", records.size());
        response.put("totalImpact", service.totalFinancialValue());
        response.put("meanConfidence", service.averageConfidence());
        response.put("data", records);
        return response;
    }

    public Map<String, Object> findById(String id) {
        Optional<SscBoardPassRatePredictorEntity> entity = service.getById(id);
        Map<String, Object> response = new HashMap<>();
        if (entity.isPresent()) {
            response.put("statusCode", 200);
            response.put("status", "FOUND");
            response.put("data", entity.get());
        } else {
            response.put("statusCode", 404);
            response.put("status", "NOT_FOUND");
            response.put("error", "No entity found for ID: " + id);
        }
        return response;
    }

    public Map<String, Object> createEntry(SscBoardPassRatePredictorRequestDto request) {
        Map<String, Object> response = new HashMap<>();
        try {
            SscBoardPassRatePredictorEntity created = service.executeWorkflow(request);
            response.put("statusCode", 201);
            response.put("status", "CREATED");
            response.put("id", created.getId());
            response.put("reference", created.getReferenceCode());
            response.put("data", created);
        } catch (IllegalArgumentException e) {
            response.put("statusCode", 400);
            response.put("status", "BAD_REQUEST");
            response.put("error", e.getMessage());
        }
        return response;
    }

    public Map<String, Object> updateStatus(String id, String newStatus, String notes) {
        boolean updated = service.updateExecutionStatus(id, newStatus, notes);
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", updated ? 200 : 404);
        response.put("status", updated ? "UPDATED" : "NOT_FOUND");
        response.put("id", id);
        return response;
    }

    public Map<String, Object> deleteEntry(String id) {
        boolean deleted = service.purge(id);
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", deleted ? 200 : 404);
        response.put("status", deleted ? "DELETED" : "NOT_FOUND");
        response.put("id", id);
        return response;
    }
}
