package com.user.details.userdetails.controllers;

import com.user.details.userdetails.dtos.LogOutReqDto;
import com.user.details.userdetails.dtos.LoginReqDto;
import com.user.details.userdetails.dtos.SignUpRequestDto;

import com.user.details.userdetails.models.Tokens;
import com.user.details.userdetails.models.User;
import com.user.details.userdetails.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Junit For Class User Controller
 */
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user;

    private Tokens tokens;

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
    }

    /**
     * Junit Test Cases For signup Api
     */
    @Test
    public void signUp() {
        when(userService.signUp(any(), any(), any())).thenReturn(user);
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setEmail(user.getEmail());
        signUpRequestDto.setPassword(user.getPassword());
        signUpRequestDto.setName(user.getName());
        assertNotNull(userController.signUp(signUpRequestDto));
    }

    /**
     * Junit Test Cases For logout Api
     *
     * @throws Exception
     */
    @Test
    public void logout() throws Exception {
        doNothing().when(userService).logOut(any());
        LogOutReqDto logoutreqdto = new LogOutReqDto();
        logoutreqdto.setToken(tokens.getValue());
        userController.logout(logoutreqdto);
        assertNotNull(tokens.getValue());
    }

    /**
     * Junit Test Cases For login Api
     *
     * @throws Exception
     */
    @Test
    public void login() throws Exception {
        when(userService.login(any(), any())).thenReturn(tokens);
        LoginReqDto loginreqdto = new LoginReqDto();
        loginreqdto.setEmail(user.getEmail());
        loginreqdto.setPassword(user.getPassword());
        loginreqdto.setName(user.getName());
        assertNotNull(userController.login(loginreqdto));
    }

    /**
     * Junit Test Cases For Validate Api
     *
     * @throws Exception
     */
    @Test
    public void validate() throws Exception {
        when(userService.ValidateToken(any())).thenReturn(user);
        assertNotNull(userController.validate(tokens.getValue()));
    }
}