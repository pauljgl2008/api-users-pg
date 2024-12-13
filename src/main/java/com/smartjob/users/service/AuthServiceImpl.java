package com.smartjob.users.service;

import com.smartjob.users.common.JwtProperties;
import com.smartjob.users.common.ValidationConstants;
import com.smartjob.users.exception.InvalidFieldException;
import com.smartjob.users.model.dto.auth.AuthLoginRequestDto;
import com.smartjob.users.model.dto.auth.AuthLoginResponseDto;
import com.smartjob.users.model.entity.User;
import com.smartjob.users.repository.UserRepositoryImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

import static java.util.Base64.getDecoder;

@EnableConfigurationProperties(JwtProperties.class)
@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final JwtProperties tokenJwtProperties;
    private final UserRepositoryImpl userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(this.getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getTokenBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(String subject) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setIssuer(this.tokenJwtProperties.getIssuer())
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + this.tokenJwtProperties.getExpiredTime()))
                .signWith(this.getSecretKey())
                .compact();
    }

    @Override
    public AuthLoginResponseDto login(AuthLoginRequestDto authLoginRequestDto) {
        User userFound = this.validateExistsUserByEmail(authLoginRequestDto.getEmail());
        if (this.validatePassword(authLoginRequestDto.getPassword(), userFound.getPassword())) {
            String newToken = generateToken(authLoginRequestDto.getEmail());
            userFound.setLastLogin(Instant.now());
            userFound.setToken(newToken);
            this.userRepository.save(userFound);
            return new AuthLoginResponseDto(userFound.getToken());
        } else {
            throw new InvalidFieldException(HttpStatus.BAD_REQUEST, "password", authLoginRequestDto.getPassword(),
                    ValidationConstants.PASSWORD_INVALID_MESSAGE);
        }
    }

    public boolean validatePassword(String passwordActual, String passwordExpected) {
        return this.encoder.matches(passwordActual, passwordExpected);
    }

    private User validateExistsUserByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidFieldException(HttpStatus.BAD_REQUEST, "email", email,
                                ValidationConstants.EMAIL_NOT_EXISTS_MESSAGE));
    }

    private Key getSecretKey() {
        byte[] decodedKey = getDecoder().decode(this.tokenJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(decodedKey);
    }

}