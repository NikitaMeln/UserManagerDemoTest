package com.test.example_23.demotest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Builder
public class RegistrationUserDto {
    @NotBlank(message = "Email field, can not be empty!")
    private String email;
    @NotBlank(message = "First name field, can not be empty!")
    private String firstName;
    @NotBlank(message = "Last name field, can not be empty!")
    private String lastName;
    @NotNull(message = "Birthday field, can not be empty. And your age must be older than 18 y.o.!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;
    private String address;
    private String phone;
}
