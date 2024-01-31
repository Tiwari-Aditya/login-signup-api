package com.aditya.loginsignup.repository;

import com.aditya.loginsignup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserNameOrEmail(String username,String email);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
}
