package com.user.details.userdetails.dtos;

import com.user.details.userdetails.enums.ResponseStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResDto {

    private String token;
    private ResponseStatusEnum status;
}
