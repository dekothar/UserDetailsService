package com.user.details.userdetails.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Tokens extends BaseModel {

    private String value;
    private Date expiryAt;
    @ManyToOne
    private User user;
}
