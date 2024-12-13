package com.smartjob.users.service;

import com.smartjob.users.common.JwtProperties;
import com.smartjob.users.exception.InvalidFieldException;
import com.smartjob.users.model.dto.auth.AuthLoginRequestDto;
import com.smartjob.users.model.entity.User;
import com.smartjob.users.repository.UserRepositoryImpl;
import com.smartjob.users.util.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceImpl Tests")
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private JwtProperties jwtProperties;

    private static AuthLoginRequestDto authLoginRequestDto;

    private static final String AUTH_LOGIN_REQUEST_JSON_PATH = "src/test/resources/AuthLoginRequest.json";

    private static final String JWT_SECRET = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";

    private static final String JWT_ISSUER = "www.smartjob.com";

    private static final String TOKEN_HEADER = "eyJhbGciOiJIUzM4NCJ9";

    private static final String EMAIL_TEST = "test@smartjob.com";

    private static User user;

    @BeforeAll
    public static void setUp() throws IOException {
        user = new User();
        user.setPassword("hunter3");
        user.setEmail("test@email.com");
        user.setToken("123123123123123");
        authLoginRequestDto = TestUtil.convertJsonToDto(AUTH_LOGIN_REQUEST_JSON_PATH, AuthLoginRequestDto.class);
    }

    @Test
    @DisplayName("Login User Success")
    void given_validCredentials_when_loginCalled_then_returnsValidToken() {
        when(AuthServiceImplTest.this.userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(AuthServiceImplTest.this.encoder.matches(any(), any())).thenReturn(true);
        when(AuthServiceImplTest.this.jwtProperties.getSecret()).thenReturn(JWT_SECRET);
        when(AuthServiceImplTest.this.jwtProperties.getIssuer()).thenReturn(JWT_ISSUER);
        when(AuthServiceImplTest.this.userRepository.save(user)).thenReturn(user);
        AuthServiceImplTest.this.authService.login(authLoginRequestDto);
        final var actual = AuthServiceImplTest.this.authService.login(authLoginRequestDto);
        assert (actual.getToken().contains(TOKEN_HEADER));
    }

    @Test
    @DisplayName("Validate Email User Failed")
    void given_existingUser_when_validateNotExistsUserByEmail_then_shouldThrowInvalidFieldException() {
        when(AuthServiceImplTest.this.userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        assertThrows(InvalidFieldException.class, () -> AuthServiceImplTest.this.authService.login(authLoginRequestDto));
        verify(AuthServiceImplTest.this.userRepository).findByEmail(any(String.class));
    }

    @Test
    @DisplayName("Validate Password Regex User Failed")
    void given_invalidPassword_when_validatePasswordRegexUser_then_shouldThrowInvalidFieldException() {
        authLoginRequestDto.setPassword("failed");
        when(AuthServiceImplTest.this.userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        assertThrows(InvalidFieldException.class, () -> AuthServiceImplTest.this.authService.login(authLoginRequestDto));
        verify(AuthServiceImplTest.this.userRepository).findByEmail(any(String.class));
    }

    @Test
    @DisplayName("Validate Token Success")
    void given_validToken_when_validateToken_then_shouldReturnTrue() {
        when(AuthServiceImplTest.this.jwtProperties.getExpiredTime()).thenReturn(60000L);
        when(AuthServiceImplTest.this.jwtProperties.getSecret()).thenReturn(JWT_SECRET);
        when(AuthServiceImplTest.this.jwtProperties.getIssuer()).thenReturn(JWT_ISSUER);
        final var token = AuthServiceImplTest.this.authService.generateToken(EMAIL_TEST);
        final var result = AuthServiceImplTest.this.authService.isValidToken(token);
        assertTrue(result);
    }

    @Test
    @DisplayName("Get Token Body Success")
    void given_validToken_when_validateToken_then_shouldReturnTokenBody() {
        when(AuthServiceImplTest.this.jwtProperties.getExpiredTime()).thenReturn(60000L);
        when(AuthServiceImplTest.this.jwtProperties.getSecret()).thenReturn(JWT_SECRET);
        when(AuthServiceImplTest.this.jwtProperties.getIssuer()).thenReturn(JWT_ISSUER);

        final var token = AuthServiceImplTest.this.authService.generateToken(EMAIL_TEST);
        final var result = AuthServiceImplTest.this.authService.getTokenBody(token);

        assertEquals(JWT_ISSUER, result.getIssuer());
        assertEquals(EMAIL_TEST, result.getSubject());
    }

    @Test
    @DisplayName("Validate Token Failed")
    void given_validToken_when_validateToken_then_shouldReturnFalse() {
        when(AuthServiceImplTest.this.jwtProperties.getSecret()).thenReturn(JWT_SECRET);
        when(AuthServiceImplTest.this.jwtProperties.getIssuer()).thenReturn(JWT_ISSUER);

        final var token = AuthServiceImplTest.this.authService.generateToken(EMAIL_TEST);
        final var result = AuthServiceImplTest.this.authService.isValidToken(token);

        assertFalse(result);
    }

}
