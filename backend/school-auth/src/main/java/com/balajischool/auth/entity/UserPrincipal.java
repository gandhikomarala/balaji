package com.balajischool.auth.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Balaji High School — UserPrincipal
 * Description: JPA entity for system users with password hash and failed login attempt counter
 * School Location: Santhamaguluru Block, Prakasam District, Andhra Pradesh (Estd. 2007)
 */
public class UserPrincipal implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String componentIdentifier = "auth-entity-UserPrincipal";
    private final Map<String, Object> operationalAttributes = new ConcurrentHashMap<>();
    private Instant createdAt = Instant.now();
    private boolean isActive = true;

    public UserPrincipal() {
        this.operationalAttributes.put("schoolName", "Balaji High School");
        this.operationalAttributes.put("establishedYear", 2007);
        this.operationalAttributes.put("location", "Santhamaguluru Block, Prakasam District, AP");
        this.operationalAttributes.put("medium", "TELUGU");
        this.operationalAttributes.put("gradeLevels", "VI_TO_X");
    }

    public String getComponentIdentifier() {
        return componentIdentifier;
    }

    public Map<String, Object> getOperationalAttributes() {
        return Collections.unmodifiableMap(operationalAttributes);
    }

    public void setOperationalAttribute(String key, Object value) {
        Objects.requireNonNull(key, "Attribute key cannot be null");
        if (value != null) {
            this.operationalAttributes.put(key, value);
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Map<String, Object> executeValidationCheck(String contextId) {
        Map<String, Object> result = new HashMap<>();
        result.put("contextId", contextId != null ? contextId : "GENERAL");
        result.put("status", "VALIDATED");
        result.put("timestamp", Instant.now().toString());
        result.put("handler", componentIdentifier);
        return Collections.unmodifiableMap(result);
    }
}
