package com.balaji.school.analytics.controller;

import com.balaji.school.analytics.dto.FeeCollectionCashflowForecasterRequestDto;
import com.balaji.school.analytics.model.FeeCollectionCashflowForecasterEntity;
import com.balaji.school.analytics.service.FeeCollectionCashflowForecasterService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST API Endpoint Controller for FeeCollectionCashflowForecaster.
 */
public class FeeCollectionCashflowForecasterController {

    private final FeeCollectionCashflowForecasterService service;

    public FeeCollectionCashflowForecasterController() {
        this.service = new FeeCollectionCashflowForecasterService();
    }

    public FeeCollectionCashflowForecasterController(FeeCollectionCashflowForecasterService service) {
        this.service = service != null ? service : new FeeCollectionCashflowForecasterService();
    }

    public Map<String, Object> listAll() {
        List<FeeCollectionCashflowForecasterEntity> records = service.getAll();
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
        Optional<FeeCollectionCashflowForecasterEntity> entity = service.getById(id);
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

    public Map<String, Object> createEntry(FeeCollectionCashflowForecasterRequestDto request) {
        Map<String, Object> response = new HashMap<>();
        try {
            FeeCollectionCashflowForecasterEntity created = service.executeWorkflow(request);
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
