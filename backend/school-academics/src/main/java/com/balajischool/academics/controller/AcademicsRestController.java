package com.balajischool.academics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

/**
 * REST API Controller for school-academics
 */
@RestController
@RequestMapping("/api/v1/academics")
@CrossOrigin(origins = "*")
public class AcademicsRestController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getHealthStatus() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("module", "school-academics");
        resp.put("status", "UP");
        resp.put("school", "Balaji High School (Santhamaguluru)");
        resp.put("timestamp", Instant.now().toString());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/records")
    public ResponseEntity<List<Map<String, Object>>> getModuleRecords(
            @RequestParam(defaultValue = "Grade IX") String grade,
            @RequestParam(defaultValue = "2026-2027") String academicYear) {
        
        List<Map<String, Object>> records = new ArrayList<>();
        Map<String, Object> r1 = new HashMap<>();
        r1.put("id", "BHS-" + "academics".toUpperCase() + "-001");
        r1.put("grade", grade);
        r1.put("academicYear", academicYear);
        r1.put("medium", "TELUGU");
        r1.put("status", "VERIFIED");
        r1.put("metric", new BigDecimal("94.50"));
        records.add(r1);

        return ResponseEntity.ok(records);
    }

    @PostMapping("/action")
    public ResponseEntity<Map<String, Object>> executeModuleAction(@RequestBody Map<String, Object> payload) {
        Map<String, Object> res = new HashMap<>();
        res.put("transactionId", UUID.randomUUID().toString());
        res.put("module", "school-academics");
        res.put("status", "SUCCESS");
        res.put("processedAt", Instant.now().toString());
        return ResponseEntity.ok(res);
    }
}
