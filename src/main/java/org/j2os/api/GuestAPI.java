package org.j2os.api;

import org.j2os.common.TokenProvider;
import org.j2os.model.UserEntity;
import org.j2os.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

/*
    Bahador, Amirsam
 */
@RestController
public class GuestAPI {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public GuestAPI(UserService userService, TokenProvider tokenProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/create-user")//change method type, after learning (@PostMapping)
    public void createUser(@ModelAttribute UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userService.save(userEntity);
    }

    @GetMapping("/login")//change method type, after learning (@PostMapping)
    public String login(@ModelAttribute UserEntity userEntity) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword()));
        return tokenProvider.getToken(userEntity.getUsername());
    }
}
