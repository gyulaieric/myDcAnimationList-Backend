package com.example.myDCAnimationList.controller;

import com.example.myDCAnimationList.model.Animation;
import com.example.myDCAnimationList.repository.RoleRepository;
import com.example.myDCAnimationList.repository.UserRepository;
import com.example.myDCAnimationList.service.AnimationService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnimationController.class)
public class AnimationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimationService animationService;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser
    public void getAnimations() throws Exception {
        Long firstAnimationId = 1L;
        Animation firstAnimation = new Animation();
        firstAnimation.setId(firstAnimationId);

        Long secondAnimationId = 2L;
        Animation secondAnimation = new Animation();
        secondAnimation.setId(secondAnimationId);

        Mockito.when(animationService.getAnimations()).thenReturn(List.of(firstAnimation, secondAnimation));

        mockMvc.perform(get("/animations/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(firstAnimationId.intValue())))
                .andExpect(jsonPath("$[1].id", CoreMatchers.is(secondAnimationId.intValue())));
    }

    @Test
    @WithMockUser
    public void getAnimationById() throws Exception{
        Long animationId = 1L;
        Animation animation = new Animation();
        animation.setId(animationId);

        Mockito.when(animationService.getAnimationById(animationId)).thenReturn(animation);

        mockMvc.perform(get("/animations/" + animationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id ", CoreMatchers.is(animationId.intValue())));
    }
}
