package com.example.myDCAnimationList.service;

import com.example.myDCAnimationList.model.Animation;
import com.example.myDCAnimationList.model.User;
import com.example.myDCAnimationList.model.UserAnimationJunction;
import com.example.myDCAnimationList.repository.AnimationRepository;
import com.example.myDCAnimationList.repository.UserAnimationJunctionRepository;
import com.example.myDCAnimationList.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserAnimationJunctionServiceTest {
    @Mock
    private UserAnimationJunctionRepository junctionRepository;

    @Mock
    private AnimationRepository animationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserAnimationJunctionService junctionService;

    private User user;
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        Long userId = 2L;
        user = new User();
        user.setId(userId);
        user.setUsername("testuser");

        authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void getList() {
        Long userId = 1L;
        UserAnimationJunction junction = new UserAnimationJunction();
        junction.setUserId(userId);

        List<UserAnimationJunction> list = Collections.singletonList(junction);

        Mockito.when(junctionRepository.findAllByUserId(userId)).thenReturn(list);

        List<UserAnimationJunction> retrievedList = junctionService.getList(userId);

        assertEquals(list.size(), retrievedList.size());
    }

    @Test
    public void saveJunction() {
        Animation animation = new Animation();
        animation.setId(1L);

        Mockito.when(authentication.getName()).thenReturn("testuser");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Mockito.when(animationRepository.existsById(animation.getId())).thenReturn(true);

        junctionService.saveJunction(authentication, animation.getId());

        Mockito.verify(junctionRepository).save(Mockito.any(UserAnimationJunction.class));
    }

    @Test
    public void saveJunction_NonExistentAnimation_ShouldThrowIllegalStateException() {
        Long nonExistentAnimationId = 1L;

        Mockito.when(authentication.getName()).thenReturn("testuser");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Mockito.when(animationRepository.existsById(Mockito.anyLong())).thenReturn(false);

       IllegalStateException exception = assertThrows(IllegalStateException.class, () -> junctionService.saveJunction(authentication, nonExistentAnimationId));
       assertEquals("Animation does not exist", exception.getMessage());
    }

    @Test
    public void saveJunction_AnimationAlreadyInList_ShouldThrowIllegalStateException() {
        Long animationId = 1L;

        UserAnimationJunction junction = new UserAnimationJunction();
        junction.setAnimationId(animationId);

        Mockito.when(authentication.getName()).thenReturn("testuser");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Mockito.when(junctionRepository.findAllByUserId(user.getId())).thenReturn(Collections.singletonList(junction));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> junctionService.saveJunction(authentication, animationId));
        assertEquals("Animation is already in your list", exception.getMessage());
    }

    @Test
    public void updateUserRating() {
        Long id = 1L;
        UserAnimationJunction junction = new UserAnimationJunction();
        junction.setId(id);
        junction.setUserId(user.getId());

        Mockito.when(junctionRepository.findById(id)).thenReturn(Optional.of(junction));

        UserAnimationJunction listItem = new UserAnimationJunction();
        listItem.setUserRating(7.5);


        Mockito.when(authentication.getName()).thenReturn("testuser");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        junctionService.updateUserRating(authentication, id, listItem);
        Mockito.verify(junctionRepository).save(junction);
    }

    @Test
    public void updateUserRating_NonExistentListItem_ShouldThrowIllegalStateException() {
        Long nonExistentListItemId = 1L;
        Mockito.when(junctionRepository.findById(nonExistentListItemId)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> junctionService.updateUserRating(authentication, nonExistentListItemId, new UserAnimationJunction()));
        assertEquals("List item with id " + nonExistentListItemId + " doesn't exist", exception.getMessage());
    }

    @Test
    public void updateUserRatting_RatingGreaterThanTen_ShouldThrowIllegalStateException() {
        Long id = 1L;
        UserAnimationJunction junction = new UserAnimationJunction();
        junction.setId(id);

        Mockito.when(junctionRepository.findById(id)).thenReturn(Optional.of(junction));

        UserAnimationJunction listItem = new UserAnimationJunction();
        listItem.setUserRating(11.5);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> junctionService.updateUserRating(authentication, id, listItem));
        assertEquals("Invalid rating", exception.getMessage());
    }

    @Test
    public void updateUserRatting_RatingLessThanZero_ShouldThrowIllegalStateException() {
        Long id = 1L;
        UserAnimationJunction junction = new UserAnimationJunction();
        junction.setId(id);

        Mockito.when(junctionRepository.findById(id)).thenReturn(Optional.of(junction));

        UserAnimationJunction listItem = new UserAnimationJunction();
        listItem.setUserRating(-2.1);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> junctionService.updateUserRating(authentication, id, listItem));
        assertEquals("Invalid rating", exception.getMessage());
    }

    @Test
    public void updateUserRating_AnimationNotInUsersList_ShouldThrowIllegalStateException() {
        Long id = 1L;
        UserAnimationJunction junction = new UserAnimationJunction();
        junction.setId(id);
        junction.setUserId(3L);

        Mockito.when(junctionRepository.findById(id)).thenReturn(Optional.of(junction));

        UserAnimationJunction listItem = new UserAnimationJunction();
        listItem.setUserRating(9.99);

        Mockito.when(authentication.getName()).thenReturn("testuser");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> junctionService.updateUserRating(authentication, id, listItem));
        assertEquals("You cannot rate this animation.", exception.getMessage());
    }

    @Test
    public void deleteJunction() {
        Long animationId = 1L;
        UserAnimationJunction junction = new UserAnimationJunction();
        junction.setId(1L);
        junction.setUserId(user.getId());
        junction.setAnimationId(animationId);

        Mockito.when(authentication.getName()).thenReturn("testuser");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Mockito.when(junctionRepository.findAllByUserId(user.getId())).thenReturn(Collections.singletonList(junction));

        junctionService.deleteJunction(authentication, animationId);
        Mockito.verify(junctionRepository).deleteById(Mockito.anyLong());
    }

    @Test
    public void deleteJunction_AnimationNotInUsersList_ShouldThrowIllegalStateException() {
        Long animationId = 1L;
        UserAnimationJunction junction = new UserAnimationJunction();
        junction.setId(1L);
        junction.setUserId(user.getId());
        junction.setAnimationId(animationId);

        Mockito.when(junctionRepository.findAllByUserId(user.getId())).thenReturn(Arrays.asList(new UserAnimationJunction(user.getId(), 2L)));
        Mockito.when(authentication.getName()).thenReturn("testuser");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> junctionService.deleteJunction(authentication, animationId));
        assertEquals("Animation is not in your list", exception.getMessage());
    }
}
