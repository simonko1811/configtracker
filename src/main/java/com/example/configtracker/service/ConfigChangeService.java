package com.example.configtracker.service;

import com.example.configtracker.dto.ConfigChangeRequest;
import com.example.configtracker.external.NotifierService;
import com.example.configtracker.model.ChangeType;
import com.example.configtracker.model.ConfigChange;
import org.springframework.stereotype.Service;
import io.micrometer.core.instrument.MeterRegistry;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ConfigChangeService {

    private final Map<String, ConfigChange> changeStore = new ConcurrentHashMap<>();
    private final NotifierService notifierService;
    private final MeterRegistry meterRegistry;

    public ConfigChangeService(NotifierService notifierService, MeterRegistry meterRegistry) {
        this.notifierService = notifierService;
        this.meterRegistry = meterRegistry;
    }

    public ConfigChange createChange(ConfigChangeRequest request) {
        ConfigChange change = new ConfigChange(
                request.getType(),
                request.getDescription(),
                request.isCritical()
        );
        changeStore.put(change.getId(), change);

        if (change.isCritical()) {
            notifierService.notifyExternalSystem(change);
        }

        meterRegistry.counter("config.change.count", "type", change.getType().toString()).increment();

        return change;
    }

    public List<ConfigChange> listChanges(Optional<ChangeType> type, Optional<Instant> since) {
        return changeStore.values().stream()
                .filter(c -> type.map(t -> c.getType() == t).orElse(true))
                .filter(c -> since.map(s -> c.getTimestamp().isAfter(s)).orElse(true))
                .sorted(Comparator.comparing(ConfigChange::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    public Optional<ConfigChange> getChangeById(String id) {
        return Optional.ofNullable(changeStore.get(id));
    }
}
