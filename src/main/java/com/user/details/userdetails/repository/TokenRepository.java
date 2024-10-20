package com.user.details.userdetails.repository;

import com.user.details.userdetails.models.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Tokens, Long> {
}
