package com.user.details.userdetails.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity(name = "users_details")
public class User extends BaseModel {

    private String name;
    private String email;
    private String password;
    @ManyToMany
    private List<RoleDetails> roles;
}
