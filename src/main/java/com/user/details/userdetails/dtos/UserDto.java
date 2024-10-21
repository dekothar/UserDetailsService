package com.user.details.userdetails.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user.details.userdetails.enums.ResponseStatusEnum;
import com.user.details.userdetails.models.RoleDetails;
import com.user.details.userdetails.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private List<RoleDetails> roles;

    @JsonIgnore
    private ResponseStatusEnum responseStatus;

    /**
     * This method is used to convert Entity Object into Dto
     *
     * @param user
     * @return UserDto
     */
    public static UserDto convertFromUserToUserDto(User user) {
        if (user == null) return null;
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
