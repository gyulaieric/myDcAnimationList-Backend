package com.example.myDCAnimationList.model;

import jakarta.persistence.*;

@Entity
@Table(name = "list")
public class UserAnimationJunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long animationId;

    private Double userRating;

    public UserAnimationJunction() { }

    public UserAnimationJunction(Long user_id, Long animation_id) {
        this.userId = user_id;
        this.animationId = animation_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnimationId() {
        return animationId;
    }

    public void setAnimationId(Long animationId) {
        this.animationId = animationId;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }
}
