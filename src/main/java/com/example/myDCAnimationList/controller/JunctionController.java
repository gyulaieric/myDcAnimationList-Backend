package com.example.myDCAnimationList.controller;

import com.example.myDCAnimationList.model.UserAnimationJunction;
import com.example.myDCAnimationList.service.UserAnimationJunctionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("http://127.0.0.1:5500")
@RestController
@RequestMapping("/list")
public class JunctionController {

    private final UserAnimationJunctionService junctionService;

    public JunctionController(UserAnimationJunctionService junctionService) {
        this.junctionService = junctionService;
    }

    @PostMapping("/add/{animationId}")
    public Map<String, String> saveJunction(Authentication authentication, @PathVariable Long animationId) {
        return junctionService.saveJunction(authentication, animationId);
    }

    @GetMapping("{userId}")
    public List<UserAnimationJunction> getList(@PathVariable Long userId) {
        return junctionService.getList(userId);
    }

    @PutMapping("/rate/{listItemId}")
    public Map<String, String> updateUserRating(Authentication authentication, @PathVariable Long listItemId, @RequestBody UserAnimationJunction listItem) {
        return junctionService.updateUserRating(authentication, listItemId, listItem);
    }

    @DeleteMapping("/delete/{animationId}")
    public void deleteJunction(Authentication authentication, @PathVariable Long animationId) {
        junctionService.deleteJunction(authentication, animationId);
    }
}
