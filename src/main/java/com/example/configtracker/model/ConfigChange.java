package com.example.configtracker.model;

import java.time.Instant;
import java.util.UUID;

public class ConfigChange {
    private final String id;
    private final ChangeType type;
    private final String description;
    private final boolean critical;
    private final Instant timestamp;

    public ConfigChange(ChangeType type, String description, boolean critical) {
        this.id = UUID.randomUUID().toString(); //generates 128-bit unique random value for ID
        this.type = type;
        this.description = description;
        this.critical = critical;
        this.timestamp = Instant.now();
    }

    // getters
    public String getId() { return id; }

    public ChangeType getType() { return type; }

    public String getDescription() { return description; }

    public boolean isCritical() { return critical; }

    public Instant getTimestamp() { return timestamp; }
}
