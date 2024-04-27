package com.test.example_23.demotest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    @NotNull
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDay;
    private String address;
    private String phone;
}
