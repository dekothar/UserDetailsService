package com.user.details.userdetails.controllers;

import com.user.details.userdetails.dtos.*;
import com.user.details.userdetails.enums.ResponseStatusEnum;
import com.user.details.userdetails.models.Tokens;
import com.user.details.userdetails.models.User;
import com.user.details.userdetails.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")

/**
 * This controller will Performs the following functionality
 * 1.Login Functionality
 * 2.Logout Functionality
 * 3.Signup Functionality
 * 4.Validate Tokens Functionality
 * @Author DeepKothari
 */
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //1. SignUp Functionality
    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto signupreqdto) {
        User user = userService.signUp(signupreqdto.getName(), signupreqdto.getEmail(), signupreqdto.getPassword());
        return UserDto.convertFromUserToUserDto(user);
    }

    // 2. login functionality
    @PostMapping("/login")
    public TokenResDto login(@RequestBody LoginReqDto loginreqdto) {
        TokenResDto response = new TokenResDto();
        try {
            Tokens tokens = userService.login(loginreqdto.getEmail(), loginreqdto.getPassword());
            response.setToken(tokens.getValue());
            response.setStatus(ResponseStatusEnum.SUCCESS);
        } catch (Exception e) {
            response.setStatus(ResponseStatusEnum.FAILURE);
        }
        return response;
    }

    // 3. logout functionality
    @PatchMapping("/logout")
    public void logout(@RequestBody LogOutReqDto logoutreqdto) {
        try {
            userService.logOut(logoutreqdto.getToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 4. validate functionality
    @GetMapping("/validate/{token}")
    public UserDto validate(@PathVariable String token) {
        UserDto userDto = null;
        try {
            User user = userService.ValidateToken(token);
            userDto = UserDto.convertFromUserToUserDto(user);
            userDto.setResponseStatus(ResponseStatusEnum.SUCCESS);
        } catch (Exception e) {
            userDto = new UserDto();
            userDto.setResponseStatus(ResponseStatusEnum.FAILURE);
        }
        return userDto;
    }

    // 5. Sample Api which will be called from ProductSService
    // to evenly distribute the api requests on different server port registered with Service Discovery
    // instead of hardcoding the port
    // it will be taken care by load balancer by using annotation loadbalanced
    @GetMapping("/details/{id}")
    public UserDto details(@PathVariable Long id) {
        System.out.println("getting user details here" + id);
        return new UserDto();
    }
}
