package com.itqgruop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itqgruop.config.MongoConfig;
import com.itqgruop.facade.OrderNumberFacade;
import com.itqgruop.mapper.OrderNumberMapper;
import com.itqgruop.repository.MongoOrderNumberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class NumberControllerTest extends MongoConfig {

    @Autowired
    private MockMvc mockMvc;

    private static final String API_URL = "/api/v1/numbers";

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGetOrderNumber_ReturnsGeneratedNumber() throws Exception {
        // Act & Assert
        mockMvc.perform(get(API_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").isNotEmpty());
    }
}
