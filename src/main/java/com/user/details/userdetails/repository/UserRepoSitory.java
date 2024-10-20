package com.user.details.userdetails.repository;

import com.user.details.userdetails.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepoSitory extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
