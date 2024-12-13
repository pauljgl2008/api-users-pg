package com.smartjob.users.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.smartjob.users.model.dto.user.UserRequestDto;
import com.smartjob.users.model.dto.user.UserResponseDto;
import com.smartjob.users.service.UserService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserController Tests")
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private static List<UserResponseDto> getUsersResponse;

    private static UserRequestDto userRequestDto;

    private static UserResponseDto userResponseDto;

    private static final String USER_REQUEST_JSON_PATH = "src/test/resources/UserRequest.json";

    private static final String USER_RESPONSE_JSON_PATH = "src/test/resources/UserResponse.json";

    private static final String GET_USERS_RESPONSE_JSON_PATH = "src/test/resources/GetUsersResponse.json";

    @BeforeAll
    public static void setUp() throws IOException {
        userRequestDto = TestUtil.convertJsonToDto(USER_REQUEST_JSON_PATH, UserRequestDto.class);
        userResponseDto = TestUtil.convertJsonToDto(USER_RESPONSE_JSON_PATH, UserResponseDto.class);
        getUsersResponse = TestUtil.convertJsonToDto(GET_USERS_RESPONSE_JSON_PATH, new TypeReference<>() {
        });
    }

    @Test
    @DisplayName("Create User Success")
    void given_validUserRequest_when_createUserCalled_then_returnsCreatedAndUser() {
        when(this.userService.createUser(userRequestDto)).thenReturn(userResponseDto);

        final var result = UserControllerTest.this.userController.createUser(userRequestDto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody().getId());
        assertNotNull(result.getBody().getCreated());
        assertNotNull(result.getBody().getToken());
        assertNotNull(result.getBody().getModified());
        assertNotNull(result.getBody().getLastLogin());
        assertNotNull(result.getBody().getIsActive());
        verify(this.userService).createUser(any(UserRequestDto.class));
    }

    @Test
    @DisplayName("Success Get Users")
    void given_requestGetUsers_when_getUsersCalled_then_returnsOkAndListOfUsers() {
        when(this.userService.getUsers()).thenReturn(getUsersResponse);

        final var result = UserControllerTest.this.userController.getUsers();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody().get(0).getId());
        verify(this.userService).getUsers();
    }

}
