package com.smartjob.users.controller;

import com.smartjob.users.model.dto.user.UserRequestDto;
import com.smartjob.users.model.dto.user.UserResponseDto;
import com.smartjob.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userCreated = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }


}
