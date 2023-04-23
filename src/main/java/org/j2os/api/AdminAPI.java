package org.j2os.api;

import org.j2os.model.UserEntity;
import org.j2os.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    Bahador, Amirsam
 */

@RestController
@RequestMapping("/admin")
public class AdminAPI {
    private final UserService userService;

    public AdminAPI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public UserEntity get() {
        return userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
