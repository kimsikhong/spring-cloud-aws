package com.example.springcloudaws.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Query(value = "update Post p set p.status = :status where p.userId = :userId")
    void updateStatusBy(PostStatus status, Long userId);

    List<Post> findAllByUserId(Long userId);
}
