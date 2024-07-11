package com.example.myDCAnimationList.service;

import com.example.myDCAnimationList.model.Animation;
import com.example.myDCAnimationList.repository.AnimationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AnimationServiceTest {
    @Mock
    private AnimationRepository animationRepository;

    @InjectMocks
    private AnimationService animationService;

    @Test
    public void getAnimations() {
        List<Animation> animations = Collections.singletonList(new Animation());
        Mockito.when(animationRepository.findAll()).thenReturn(animations);

        List<Animation> retrievedAnimations = animationService.getAnimations();

        assertEquals(animations.size(), retrievedAnimations.size());
    }

    @Test
    public void getAnimationById() {
        Long animationId = 1L;
        Animation animation = new Animation();
        animation.setId(animationId);

        Mockito.when(animationRepository.findById(animationId)).thenReturn(Optional.of(animation));

        Animation retrievedAnimation = animationService.getAnimationById(animationId);

        assertEquals(animationId, retrievedAnimation.getId());
    }

    @Test
    public void getAnimationById_NonExistentAnimation_ShouldThrowIllegalStateException() {
        Long animationId = 1L;

        Mockito.when(animationRepository.findById(animationId)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> animationService.getAnimationById(animationId));
        assertEquals("Animation with id " + animationId + " doesn't exist", exception.getMessage());
    }
}
