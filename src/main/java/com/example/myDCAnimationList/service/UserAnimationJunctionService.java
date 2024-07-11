package com.example.myDCAnimationList.service;

import com.example.myDCAnimationList.model.UserAnimationJunction;
import com.example.myDCAnimationList.repository.AnimationRepository;
import com.example.myDCAnimationList.repository.UserAnimationJunctionRepository;
import com.example.myDCAnimationList.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserAnimationJunctionService implements IUserAnimationJunctionService{

    private final UserAnimationJunctionRepository junctionRepository;
    private final UserRepository userRepository;
    private final AnimationRepository animationRepository;

    public UserAnimationJunctionService(UserAnimationJunctionRepository junctionRepository, UserRepository userRepository, AnimationRepository animationRepository) {
        this.junctionRepository = junctionRepository;
        this.userRepository = userRepository;
        this.animationRepository = animationRepository;
    }

    @Override
    public List<UserAnimationJunction> getList(Long userId) {
        return junctionRepository.findAllByUserId(userId);
    }

    @Override
    public Map<String, String> saveJunction(Authentication authentication, Long animationId) {

        UserAnimationJunction userAnimationJunction = new UserAnimationJunction(userRepository.findByUsername(authentication.getName()).get().getId(), animationId);

        if (isAnimationInList(userAnimationJunction) == null) {
            if (animationRepository.existsById(animationId)) {
                junctionRepository.save(userAnimationJunction);
                return Collections.singletonMap("success", "Animation successfully added to list");
            } else {
                return Collections.singletonMap("error", "Animation does not exist");
            }
        } else {
            return Collections.singletonMap("error", "Animation is already in your list");
        }
    }

    @Override
    public Map<String, String> updateUserRating(Authentication authentication, Long id, UserAnimationJunction listItem) {
        UserAnimationJunction userAnimationJunction = junctionRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("List item with id %s doesn't exist", id))
        );

        if (listItem.getUserRating() > 10 || listItem.getUserRating() < 0) {
            return Collections.singletonMap("error", "Invalid rating");
        }
        if (userRepository.findByUsername(authentication.getName()).get().getId().equals(junctionRepository.findById(id).get().getUserId())) {
            userAnimationJunction.setUserRating(listItem.getUserRating());
            junctionRepository.save(userAnimationJunction);
            return Collections.singletonMap("response", "Successfully updated rating.");
        } else {
            return Collections.singletonMap("error", "You are trying to rate an animation in someone else's list.");
        }
    }

    @Override
    public void deleteJunction(Authentication authentication, Long animationId) {

        UserAnimationJunction userAnimationJunction = isAnimationInList(new UserAnimationJunction(userRepository.findByUsername(authentication.getName()).get().getId(), animationId));

        if (userAnimationJunction != null) {
            junctionRepository.deleteById(userAnimationJunction.getId());
        } else {
            System.out.println("Animation is not in your list");
        }
    }

    private UserAnimationJunction isAnimationInList(UserAnimationJunction userAnimationJunction) {
        for (UserAnimationJunction junction : junctionRepository.findAllByUserId(userAnimationJunction.getUserId())) {
            if (junction.getAnimationId().equals(userAnimationJunction.getAnimationId())) {
                return junction;
            }
        }

        return null;
    }
}
