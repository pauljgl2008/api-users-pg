package com.smartjob.users.controller;

import com.smartjob.users.model.dto.auth.AuthLoginRequestDto;
import com.smartjob.users.model.dto.auth.AuthLoginResponseDto;
import com.smartjob.users.service.AuthService;
import com.smartjob.users.util.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController Tests")
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    private static AuthLoginResponseDto authLoginResponseDto;

    private static AuthLoginRequestDto authLoginRequestDto;

    private static final String AUTH_LOGIN_REQUEST_JSON_PATH = "src/test/resources/AuthLoginRequest.json";

    private static final String AUTH_LOGIN_RESPONSE_JSON_PATH = "src/test/resources/AuthLoginResponse.json";

    @BeforeAll
    public static void setUp() throws IOException {
        authLoginRequestDto = TestUtil.convertJsonToDto(AUTH_LOGIN_REQUEST_JSON_PATH, AuthLoginRequestDto.class);
        authLoginResponseDto = TestUtil.convertJsonToDto(AUTH_LOGIN_RESPONSE_JSON_PATH, AuthLoginResponseDto.class);
    }

    @Test
    @DisplayName("Login Success")
    void given_validCredentials_when_loginCalled_then_returnsOkAndToken() {
        when(this.authService.login(any(AuthLoginRequestDto.class))).thenReturn(authLoginResponseDto);

        final var result = AuthControllerTest.this.authController.login(authLoginRequestDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(authLoginResponseDto.getToken(), result.getBody().getToken());
        verify(this.authService).login(any(AuthLoginRequestDto.class));
    }

}
