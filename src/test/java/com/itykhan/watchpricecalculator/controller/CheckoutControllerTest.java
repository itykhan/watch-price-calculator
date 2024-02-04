package com.itykhan.watchpricecalculator.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Testing successful POST request")
    public void testPostSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[" +
                                "\"001\", " +
                                "\"002\", " +
                                "\"001\"," +
                                "\"004\", " +
                                "\"003\"" +
                                "]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"price\":360}"));
    }

    @Test
    @DisplayName("Testing POST request with a nonexistent watch ID")
    public void testPostWatchNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"001\", \"007\"]"))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andExpect(MockMvcResultMatchers.content().string("Could not find watch 007"));
    }

    @Test
    @DisplayName("Testing POST request with empty IDs list")
    public void testPostEmptyContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"price\":0}"));
    }

    @Test
    @DisplayName("Testing POST request without content")
    public void testPostWithoutContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    @DisplayName("Testing POST request with unsupported media type")
    public void testPostUnsupportedType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    @DisplayName("Testing unsupported GET request")
    public void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/checkout"))
                .andExpect(MockMvcResultMatchers.status().is(405));
    }
}
