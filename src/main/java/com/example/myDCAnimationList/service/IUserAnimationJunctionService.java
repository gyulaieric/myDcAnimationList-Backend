package com.example.myDCAnimationList.service;

import com.example.myDCAnimationList.model.UserAnimationJunction;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface IUserAnimationJunctionService {
    List<UserAnimationJunction> getList(Long userId);
    Map<String, String> saveJunction(Authentication authentication, Long animationId);
    Map<String, String> updateUserRating(Authentication authentication, Long id, UserAnimationJunction listItem);
    void deleteJunction(Authentication authentication, Long animationId);
}
