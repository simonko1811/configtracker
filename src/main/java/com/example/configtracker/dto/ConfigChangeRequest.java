package com.example.configtracker.dto;

import com.example.configtracker.model.ChangeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ConfigChangeRequest {

    @NotNull
    private ChangeType type;

    @NotBlank
    private String description;

    private boolean critical;

    // getters and setters
    public ChangeType getType() { return type; }

    public void setType(ChangeType type) { this.type = type; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public boolean isCritical() { return critical; }

    public void setCritical(boolean critical) { this.critical = critical; }
}
