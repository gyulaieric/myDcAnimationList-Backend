package com.example.myDCAnimationList.service;

import com.example.myDCAnimationList.model.Animation;

import java.util.List;
import java.util.Optional;

public interface IAnimationService {
    List<Animation> getAnimations();
    Animation getAnimationById(Long id);
}
