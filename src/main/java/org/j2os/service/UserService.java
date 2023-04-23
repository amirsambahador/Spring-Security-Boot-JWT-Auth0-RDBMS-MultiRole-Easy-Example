package org.j2os.service;

import lombok.extern.slf4j.Slf4j;
import org.j2os.model.UserEntity;
import org.j2os.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
    Bahador, Amirsam
 */
@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void save(UserEntity userEntity) {
        log.info("save");
        userRepository.save(userEntity);
    }

    public UserEntity findByUsername(String username) {
        log.info("findByUsername");
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername");
        UserEntity userEntity = userRepository.findByUsername(username);
        return new User(username, userEntity.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
