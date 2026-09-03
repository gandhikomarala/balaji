package com.balaji.school.finance.controller;

import com.balaji.school.finance.dto.FacultyStaffPayrollProcessorRequestDto;
import com.balaji.school.finance.model.FacultyStaffPayrollProcessorEntity;
import com.balaji.school.finance.service.FacultyStaffPayrollProcessorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Enterprise REST API Controller for FacultyStaffPayrollProcessor.
 */
public class FacultyStaffPayrollProcessorController {

    private final FacultyStaffPayrollProcessorService service;

    public FacultyStaffPayrollProcessorController() {
        this.service = new FacultyStaffPayrollProcessorService();
    }

    public FacultyStaffPayrollProcessorController(FacultyStaffPayrollProcessorService service) {
        this.service = service != null ? service : new FacultyStaffPayrollProcessorService();
    }

    public Map<String, Object> fetchAll() {
        List<FacultyStaffPayrollProcessorEntity> records = service.getAll();
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("status", "SUCCESS");
        response.put("count", records.size());
        response.put("totalMonetarySum", service.computeCumulativeAmount());
        response.put("meanScore", service.computeMeanPrimaryScore());
        response.put("payload", records);
        return response;
    }

    public Map<String, Object> fetchById(String id) {
        Optional<FacultyStaffPayrollProcessorEntity> entity = service.getById(id);
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

    public Map<String, Object> submitRecord(FacultyStaffPayrollProcessorRequestDto request) {
        Map<String, Object> response = new HashMap<>();
        try {
            FacultyStaffPayrollProcessorEntity created = service.registerEntry(request);
            response.put("statusCode", 201);
            response.put("status", "CREATED");
            response.put("id", created.getId());
            response.put("referenceNumber", created.getReferenceNumber());
            response.put("record", created);
        } catch (IllegalArgumentException e) {
            response.put("statusCode", 400);
            response.put("status", "BAD_REQUEST");
            response.put("error", e.getMessage());
        }
        return response;
    }

    public Map<String, Object> modifyStatus(String id, String newStatus, String operatorNotes) {
        boolean updated = service.updateStatus(id, newStatus, operatorNotes);
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", updated ? 200 : 404);
        response.put("status", updated ? "UPDATED" : "NOT_FOUND");
        response.put("recordId", id);
        return response;
    }

    public Map<String, Object> purgeRecord(String id) {
        boolean deleted = service.removeEntry(id);
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", deleted ? 200 : 404);
        response.put("status", deleted ? "DELETED" : "NOT_FOUND");
        response.put("recordId", id);
        return response;
    }
}
