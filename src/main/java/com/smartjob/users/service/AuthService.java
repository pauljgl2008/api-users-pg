package com.smartjob.users.service;

import com.smartjob.users.model.dto.auth.AuthLoginRequestDto;
import com.smartjob.users.model.dto.auth.AuthLoginResponseDto;
import io.jsonwebtoken.Claims;

public interface AuthService {

    boolean isValidToken(String token);

    Claims getTokenBody(String token);

    String generateToken(String subject);

    AuthLoginResponseDto login(AuthLoginRequestDto authLoginRequestDto);
}