package com.sanket.security.dao;

import com.sanket.security.model.ConfirmationToken;
import com.sanket.security.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.user.userId = ?1")
    List<Post> findPostByUserId(final Long userId);
}
