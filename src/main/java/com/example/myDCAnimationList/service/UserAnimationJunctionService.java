package com.example.myDCAnimationList.service;

import com.example.myDCAnimationList.model.UserAnimationJunction;
import com.example.myDCAnimationList.repository.AnimationRepository;
import com.example.myDCAnimationList.repository.UserAnimationJunctionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserAnimationJunctionService implements IUserAnimationJunctionService{

    private final UserAnimationJunctionRepository junctionRepository;
    private final AnimationRepository animationRepository;

    public UserAnimationJunctionService(UserAnimationJunctionRepository junctionRepository, AnimationRepository animationRepository) {
        this.junctionRepository = junctionRepository;
        this.animationRepository = animationRepository;
    }

    @Override
    public List<UserAnimationJunction> getList(Long userId) {
        return junctionRepository.findAllByUserId(userId);
    }

    @Override
    public Map<String, String> saveJunction(Long userId, Long animationId) {

        UserAnimationJunction userAnimationJunction = new UserAnimationJunction(userId, animationId);

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
    public void updateUserRating(Long id, UserAnimationJunction listItem) {
        UserAnimationJunction userAnimationJunction = junctionRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("List item with id %s doesn't exist", id))
        );

        userAnimationJunction.setUserRating(listItem.getUserRating());
        junctionRepository.save(userAnimationJunction);
    }

    @Override
    public void deleteJunction(Long userId, Long animationId) {

        UserAnimationJunction userAnimationJunction = isAnimationInList(new UserAnimationJunction(userId, animationId));

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
