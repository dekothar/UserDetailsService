package com.user.details.userdetails.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private boolean inactive;
}
