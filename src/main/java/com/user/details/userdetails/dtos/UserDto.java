package com.user.details.userdetails.dtos;

import com.user.details.userdetails.enums.ResponseStatusEnum;
import com.user.details.userdetails.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private String password;
    private ResponseStatusEnum responseStatus;

    /**
     * This method is used to convert Entity Object into Dto
     *
     * @param user * @return
     */
    public static UserDto convertFromUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
