package com.test.example_23.demotest.payload.request;

import com.test.example_23.demotest.dto.RegistrationUserDto;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    @Valid
    private RegistrationUserDto user;
    private String password;
}
