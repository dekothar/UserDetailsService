package com.user.details.userdetails.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDto {

    private String name;
    private String email;
    private String password;
}
