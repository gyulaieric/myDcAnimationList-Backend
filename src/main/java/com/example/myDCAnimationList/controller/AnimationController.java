package com.example.myDCAnimationList.controller;

import com.example.myDCAnimationList.model.Animation;
import com.example.myDCAnimationList.service.AnimationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/animations")
public class AnimationController {

    private final AnimationService animationService;

    public AnimationController(AnimationService animationService) {
        this.animationService = animationService;
    }

    @CrossOrigin
    @GetMapping("/")
    public List<Animation> getAnimations() {
        return animationService.getAnimations();
    }

    @GetMapping("{animationId}")
    public Optional<Animation> getAnimationById(@PathVariable Long animationId) {
        return animationService.getAnimationById(animationId);
    }
}
