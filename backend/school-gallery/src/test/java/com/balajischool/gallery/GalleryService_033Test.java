package com.balajischool.gallery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class GalleryService_033Test {

    private GalleryService_033 service;

    @BeforeEach
    void setUp() {
        service = new GalleryService_033();
    }

    @Test
    @DisplayName("Should successfully process academic workflow")
    void testProcessAcademicWorkflow() {
        var res = service.processAcademicWorkflow("CLASS-9A-001", new BigDecimal("92.50"), Map.of("subject", "TELUGU"));
        assertNotNull(res);
        assertTrue(res.isSuccessful());
        assertEquals(new BigDecimal("92.50"), res.computedMetric());
    }

    @Test
    @DisplayName("Should calculate attendance percentage accurately")
    void testAttendanceCalculation() {
        var pct = service.computeAttendancePercentage(200, 184);
        assertEquals(new BigDecimal("92.00"), pct);
    }
}
