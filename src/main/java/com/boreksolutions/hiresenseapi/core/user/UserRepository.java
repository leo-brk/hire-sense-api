package com.boreksolutions.hiresenseapi.core.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.deletedAt IS NULL")
    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.deletedAt IS NULL")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL")
    Page<User> findAllUsers(Pageable pageable);

}