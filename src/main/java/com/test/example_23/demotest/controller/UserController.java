package com.test.example_23.demotest.controller;

import com.test.example_23.demotest.dto.UserDto;
import com.test.example_23.demotest.entity.User;
import com.test.example_23.demotest.mapper.UserDtoMapper;
import com.test.example_23.demotest.payload.request.RegistrationRequest;
import com.test.example_23.demotest.service.UserService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserDtoMapper userDtoMapper;

    public UserController(UserService userService,
                          UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }


    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody RegistrationRequest request) {
        User user = userDtoMapper.registrationToEntity(request.getUser());
        user.setPassword(request.getPassword());
        UserDto userDto = userDtoMapper.toDto(userService.save(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {
        User updatingUser = userDtoMapper.toEntity(userDto);
        UserDto updatedUser = userDtoMapper.toDto(userService.update(updatingUser));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        User user = userService.getById(id);
        userService.deleteUser(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDtoMapper.toDto(user));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsersByBirthDateRange(@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                         LocalDate fromDate,
                                                                     @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                     LocalDate toDate) {
        List<User> filteredUsers = userService.filterByBirthDate(userService.getAll(),fromDate,toDate);
        List<UserDto> usersDto = filteredUsers.stream()
                .map(userDtoMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.FOUND).body(usersDto);
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<UserDto> usersDto = userService.getAll().stream()
                .map(userDtoMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.FOUND).body(usersDto);
    }
}
