package com.minhkuku.userservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minhkuku.userservice.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByPhone(String phone);

    Optional<User> findByPhone(String phone);
}
