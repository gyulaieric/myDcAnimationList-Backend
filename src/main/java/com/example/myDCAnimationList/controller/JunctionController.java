package com.example.myDCAnimationList.controller;

import com.example.myDCAnimationList.model.UserAnimationJunction;
import com.example.myDCAnimationList.repository.AnimationRepository;
import com.example.myDCAnimationList.repository.UserAnimationJunctionRepository;
import com.example.myDCAnimationList.repository.UserRepository;
import com.example.myDCAnimationList.service.UserAnimationJunctionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://127.0.0.1:5500")
@RestController
@RequestMapping("/list")
public class JunctionController {

    private final UserAnimationJunctionService junctionService;
    private final UserAnimationJunctionRepository junctionRepository;
    private final UserRepository userRepository;
    private final AnimationRepository animationRepository;

    public JunctionController(UserAnimationJunctionService junctionService, UserRepository userRepository, AnimationRepository animationRepository, UserAnimationJunctionRepository junctionRepository) {
        this.junctionService = junctionService;
        this.userRepository = userRepository;
        this.animationRepository = animationRepository;
        this.junctionRepository = junctionRepository;
    }

    @PostMapping("/add/{animationId}")
    public Map<String, String> saveJunction(Authentication authentication, @PathVariable Long animationId) {
        return junctionService.saveJunction(userRepository.findByUsername(authentication.getName()).get().getId(), animationId);
    }

    @GetMapping("{userId}")
    public List<UserAnimationJunction> getList(@PathVariable Long userId) {
        return junctionService.getList(userId);
    }

    @PutMapping("/rate/{listItemId}")
    public Map<String, String> updateUserRating(Authentication authentication, @PathVariable Long listItemId, @RequestBody UserAnimationJunction listItem) {
        if (listItem.getUserRating() > 10 || listItem.getUserRating() < 0) {
            return Collections.singletonMap("error", "Invalid rating");
        }
        if (userRepository.findByUsername(authentication.getName()).get().getId().equals(junctionRepository.findById(listItemId).get().getUserId())) {
            junctionService.updateUserRating(listItemId, listItem);
            return Collections.singletonMap("response", "Successfully updated rating.");
        } else {
            return Collections.singletonMap("error", "You are trying to rate an animation in someone else's list.");
        }
    }

    @DeleteMapping("/delete/{animationId}")
    public void deleteJunction(Authentication authentication, @PathVariable Long animationId) {
        junctionService.deleteJunction(userRepository.findByUsername(authentication.getName()).get().getId(), animationId);
    }
}
