package org.j2os.config;

import lombok.extern.slf4j.Slf4j;
import org.j2os.filter.SecurityFilter;
import org.j2os.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
    Bahador, Amirsam
 */
@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final SecurityFilter securityFilter;
    private final UserService userService;

    public SecurityConfig(SecurityFilter securityFilter, UserService userService) {
        this.securityFilter = securityFilter;
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("passwordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("configure");
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/*").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .userDetailsService(userService)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        log.info("passwordEncoder");
        return super.authenticationManagerBean();
    }
}
