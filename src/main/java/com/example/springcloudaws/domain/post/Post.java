package com.example.springcloudaws.domain.post;

import com.example.springcloudaws.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String title;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private PostStatus status;

    @Builder
    public Post(Long userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.status = PostStatus.DRAFT;
    }
}
