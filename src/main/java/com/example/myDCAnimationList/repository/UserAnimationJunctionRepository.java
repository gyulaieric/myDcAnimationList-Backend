package com.example.myDCAnimationList.repository;

import com.example.myDCAnimationList.model.UserAnimationJunction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAnimationJunctionRepository extends JpaRepository<UserAnimationJunction, Long> {
    List<UserAnimationJunction> findAllByUserId(Long userId);
}
