package com.test.example_23.demotest.mapper;

import com.test.example_23.demotest.dto.RegistrationUserDto;
import com.test.example_23.demotest.dto.UserDto;
import com.test.example_23.demotest.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public User registrationToEntity(RegistrationUserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .birthDay(userDto.getBirthDay())
                .address(userDto.getAddress())
                .phone(userDto.getPhone())
                .build();
    }
    public User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .birthDay(userDto.getBirthDay())
                .address(userDto.getAddress())
                .phone(userDto.getPhone())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDay(user.getBirthDay())
                .address(user.getAddress())
                .phone(user.getPhone())
                .build();
    }

}
