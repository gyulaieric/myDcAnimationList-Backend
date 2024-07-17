package com.example.myDCAnimationList.controller;

import com.example.myDCAnimationList.model.UserAnimationJunction;
import com.example.myDCAnimationList.repository.RoleRepository;
import com.example.myDCAnimationList.repository.UserAnimationJunctionRepository;
import com.example.myDCAnimationList.repository.UserRepository;
import com.example.myDCAnimationList.service.UserAnimationJunctionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JunctionController.class)
public class JunctionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAnimationJunctionService junctionService;

    @MockBean
    private UserAnimationJunctionRepository junctionRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void saveJunction() throws Exception {
        Long animationId = 1L;

        mockMvc.perform(post("/list/add/" + animationId)
                        .with(jwt()))
                .andExpect(status().isOk());

        Mockito.verify(junctionService).saveJunction(Mockito.any(Authentication.class), Mockito.anyLong());
    }

    @Test
    public void getList() throws Exception {
        Long userId = 1L;

        UserAnimationJunction junction = new UserAnimationJunction();
        junction.setId(2L);

        Mockito.when(junctionService.getList(userId)).thenReturn(Collections.singletonList(junction));

        mockMvc.perform(get("/list/" + userId)
                .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(junction.getId().intValue())));
    }

    @Test
    public void updateUserRating() throws Exception {
        Long listItemId = 1L;
        UserAnimationJunction listItem = new UserAnimationJunction();
        listItem.setId(listItemId);

        mockMvc.perform(put("/list/rate/" + listItemId)
                .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(listItem)))
                .andExpect(status().isOk());

        Mockito.verify(junctionService).updateUserRating(Mockito.any(Authentication.class), Mockito.anyLong(), Mockito.any(UserAnimationJunction.class));
    }

    @Test
    public void deleteJunction() throws Exception {
        Long animationId = 1L;

        mockMvc.perform(delete("/list/delete/" + animationId)
                .with(jwt()))
                .andExpect(status().isOk());

        Mockito.verify(junctionService).deleteJunction(Mockito.any(Authentication.class), Mockito.anyLong());
    }
}
