package com.example.myDCAnimationList.service;

import com.example.myDCAnimationList.model.UserAnimationJunction;

import java.util.List;
import java.util.Map;

public interface IUserAnimationJunctionService {
    List<UserAnimationJunction> getList(Long userId);
    Map<String, String> saveJunction(Long userId, Long animationId);
    void updateUserRating(Long id, UserAnimationJunction listItem);
    void deleteJunction(Long userId, Long animationId);
}
