package com.example.configtracker.service;

import com.example.configtracker.dto.ConfigChangeRequest;
import com.example.configtracker.model.ChangeType;
import com.example.configtracker.model.ConfigChange;
import com.example.configtracker.external.NotifierService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigChangeServiceTest {

    private ConfigChangeService service;
    private NotifierService notifierService;
    private MeterRegistry meterRegistry = new SimpleMeterRegistry();

    @BeforeEach
    void setUp() {
        //meterRegistry = mock(MeterRegistry.class);
        notifierService = mock(NotifierService.class);
        service = new ConfigChangeService(notifierService, meterRegistry);
    }

    @Test
    void createCriticalChange_shouldNotify() {
        ConfigChangeRequest request = new ConfigChangeRequest();
        request.setType(ChangeType.ADD);
        request.setDescription("Important change");
        request.setCritical(true);

        ConfigChange change = service.createChange(request);

        assertNotNull(change.getId());
        assertEquals(ChangeType.ADD, change.getType());
        assertTrue(change.isCritical());

        verify(notifierService, times(1)).notifyExternalSystem(change);
    }

    @Test
    void listChanges_shouldReturnCreatedChange() {
        ConfigChangeRequest request = new ConfigChangeRequest();
        request.setType(ChangeType.DELETE);
        request.setDescription("Test");
        request.setCritical(false);

        ConfigChange change = service.createChange(request);

        List<ConfigChange> changes = service.listChanges(Optional.empty(), Optional.empty());
        assertTrue(changes.contains(change));
    }
}
