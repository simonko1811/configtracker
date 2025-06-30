package com.example.configtracker.controller;

import com.example.configtracker.dto.ConfigChangeRequest;
import com.example.configtracker.model.ChangeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ConfigChangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createConfigChange_shouldReturn200() throws Exception {
        ConfigChangeRequest request = new ConfigChangeRequest();
        request.setType(ChangeType.ADD);
        request.setDescription("Test change");
        request.setCritical(false);

        mockMvc.perform(post("/changes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("Test change"));
    }

    @Test
    void createConfigChange_invalidInput_shouldReturn400() throws Exception {
        String invalidJson = "{\"description\":\"missing type\"}";

        mockMvc.perform(post("/changes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("must not be null"));
    }
}
