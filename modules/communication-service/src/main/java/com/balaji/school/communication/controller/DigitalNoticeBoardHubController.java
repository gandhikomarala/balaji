package com.balaji.school.communication.controller;

import com.balaji.school.communication.dto.DigitalNoticeBoardHubRequestDto;
import com.balaji.school.communication.model.DigitalNoticeBoardHubEntity;
import com.balaji.school.communication.service.DigitalNoticeBoardHubService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Enterprise REST API Controller for DigitalNoticeBoardHub.
 */
public class DigitalNoticeBoardHubController {

    private final DigitalNoticeBoardHubService service;

    public DigitalNoticeBoardHubController() {
        this.service = new DigitalNoticeBoardHubService();
    }

    public DigitalNoticeBoardHubController(DigitalNoticeBoardHubService service) {
        this.service = service != null ? service : new DigitalNoticeBoardHubService();
    }

    public Map<String, Object> fetchAll() {
        List<DigitalNoticeBoardHubEntity> records = service.getAll();
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
        Optional<DigitalNoticeBoardHubEntity> entity = service.getById(id);
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

    public Map<String, Object> submitRecord(DigitalNoticeBoardHubRequestDto request) {
        Map<String, Object> response = new HashMap<>();
        try {
            DigitalNoticeBoardHubEntity created = service.registerEntry(request);
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
