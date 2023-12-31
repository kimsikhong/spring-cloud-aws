package com.example.springcloudaws.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/v1/user/{id}:leave")
    void leave(@PathVariable Long id) {
        userService.leave(id);
    }
}
