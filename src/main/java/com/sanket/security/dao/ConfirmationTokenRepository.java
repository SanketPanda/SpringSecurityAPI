package com.sanket.security.dao;

import com.sanket.security.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByConfirmationToken(final String confirmationToken);
    @Query("SELECT c FROM ConfirmationToken c WHERE c.user.userId = ?1")
    Optional<ConfirmationToken> findByUserId(final Long userId);
}
