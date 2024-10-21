package com.user.details.userdetails.repository;

import com.user.details.userdetails.models.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Tokens, Long> {

    Optional<Tokens> findByValueAndExpiryAtGreaterThanAndInactive(String value, Date currdate, boolean inactive);
}
