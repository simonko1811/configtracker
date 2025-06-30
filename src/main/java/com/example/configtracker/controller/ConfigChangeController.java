package com.example.configtracker.controller;

import com.example.configtracker.dto.ConfigChangeRequest;
import com.example.configtracker.model.ChangeType;
import com.example.configtracker.model.ConfigChange;
import com.example.configtracker.service.ConfigChangeService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/changes")
public class ConfigChangeController {

    private final ConfigChangeService service;

    public ConfigChangeController(ConfigChangeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ConfigChange> create(@Valid @RequestBody ConfigChangeRequest request) {
        ConfigChange created = service.createChange(request);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ConfigChange>> list(
            @RequestParam Optional<ChangeType> type,
            @RequestParam Optional<String> since
    ) {
        Optional<Instant> sinceInstant = since.map(Instant::parse);
        List<ConfigChange> result = service.listChanges(type, sinceInstant);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigChange> getById(@PathVariable String id) {
        return service.getChangeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
