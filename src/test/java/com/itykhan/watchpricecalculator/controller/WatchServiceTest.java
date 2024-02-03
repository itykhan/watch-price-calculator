package com.itykhan.watchpricecalculator.controller;

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
public class WatchServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"001\"]"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/checkout"))
                .andExpect(MockMvcResultMatchers.status().is(405));
    }
}
