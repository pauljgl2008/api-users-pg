package com.smartjob.users.controller;

import com.smartjob.users.model.dto.auth.AuthLoginRequestDto;
import com.smartjob.users.model.dto.auth.AuthLoginResponseDto;
import com.smartjob.users.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDto> login(@Valid @RequestBody AuthLoginRequestDto authLoginRequestDto) {
        AuthLoginResponseDto authLoginResponseDto = authService.login(authLoginRequestDto);
        return ResponseEntity.ok(authLoginResponseDto);
    }

}
