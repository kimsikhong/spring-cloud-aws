package com.example.springcloudaws.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void leave(Long userId) {
        User user = userRepository.getReferenceById(userId);
        user.leave();
        userRepository.save(user);
    }

}
