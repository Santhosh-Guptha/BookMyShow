package com.registration.repository;

import com.registration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(Long userId);

    boolean deleteByUserId(Long userId);
}
