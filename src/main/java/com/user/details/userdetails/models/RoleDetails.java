package com.user.details.userdetails.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RoleDetails extends BaseModel {

    private String roleName;
}
