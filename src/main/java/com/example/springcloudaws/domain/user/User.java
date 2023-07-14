package com.example.springcloudaws.domain.user;

import com.example.springcloudaws.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nickname;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    @Builder
    public User(String email, String nickname, String description) {
        this.email = email;
        this.nickname = nickname;
        this.description = description;
        this.status = UserStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    void leave() {
        this.status = UserStatus.DELETED;
        registerEvent(new UserLeaveEvent(id));
    }

    public record UserLeaveEvent(Long userId) {

    }
}
