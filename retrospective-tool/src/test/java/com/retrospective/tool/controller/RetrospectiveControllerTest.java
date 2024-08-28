package com.retrospective.tool.controller;

import com.retrospective.tool.controllers.MemberController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RetrospectiveControllerTest {

    @Autowired
     MockMvc mockMvc;

    @Test
    public void createRetrospectiveTest() throws Exception {
        System.out.println(mockMvc);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("retrospective", equalTo("SUCCESS")));
    }
}
