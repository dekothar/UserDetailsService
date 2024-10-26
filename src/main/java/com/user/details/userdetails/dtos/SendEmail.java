package com.user.details.userdetails.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendEmail {

    private String to;
    private String subject;
    private String body;
}
