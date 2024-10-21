package com.user.details.userdetails.services;

import com.user.details.userdetails.models.Tokens;
import com.user.details.userdetails.models.User;
import com.user.details.userdetails.repository.TokenRepository;
import com.user.details.userdetails.repository.UserRepoSitory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

/**
 * Junit For Class UserService
 */

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepoSitory userRepoSitory;

    @Mock
    private TokenRepository tokenRepository;

    private Tokens tokens;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        tokens = new Tokens();
        user.setName("user");
        user.setPassword("password");
        user.setEmail("d@gmail.com");
        tokens.setUser(user);
        tokens.setValue("test");
        tokens.setExpiryAt(new Date());
        Optional<Tokens> optionalTokens = Optional.of(tokens);
        when(tokenRepository.findByValueAndExpiryAtGreaterThanAndInactive(any(), any(), anyBoolean())).thenReturn(optionalTokens);

    }

    /**
     * Junit Test Cases For login Functionality
     */
    @Test
    public void login() throws Exception {
        when(userRepoSitory.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);
        when(tokenRepository.save(any())).thenReturn(tokens);
        userService.login(user.getEmail(), user.getPassword());
    }

    /**
     * Junit Test Cases For signUp Functionality
     */
    @Test
    public void signUp() {
        when(bCryptPasswordEncoder.encode(any())).thenReturn(tokens.getValue());
        when(userRepoSitory.save(any())).thenReturn(user);
        assertNotNull(userService.signUp(user.getName(), user.getEmail(), user.getPassword()));

    }

    /**
     * Junit Test Cases For logout Functionality
     */
    @Test
    void logOut() throws Exception {
        when(userRepoSitory.save(any())).thenReturn(user);
        when(tokenRepository.save(any())).thenReturn(tokens);
        userService.logOut(tokens.getValue());
    }

    /**
     * Junit Test Cases For validateToken Functionality
     */
    @Test
    void validateToken() throws Exception {
        assertNotNull(userService.ValidateToken(tokens.getValue()));
    }
}