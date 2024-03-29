package com.example.myDCAnimationList.service;

import com.example.myDCAnimationList.model.Animation;
import com.example.myDCAnimationList.repository.AnimationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimationService implements IAnimationService{

    private final AnimationRepository animationRepository;

    public AnimationService(AnimationRepository animationRepository) {
        this.animationRepository = animationRepository;
    }

    @Override
    public List<Animation> getAnimations() {
        return animationRepository.findAll();
    }

    @Override
    public Optional<Animation> getAnimationById(Long id) {
        return animationRepository.findById(id);
    }
}
