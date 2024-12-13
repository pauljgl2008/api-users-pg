package com.smartjob.users.service;

import com.smartjob.users.common.ValidationRulesProperties;
import com.smartjob.users.exception.InvalidFieldException;
import com.smartjob.users.mapper.UserEntityMapperImpl;
import com.smartjob.users.model.dto.user.UserRequestDto;
import com.smartjob.users.model.entity.User;
import com.smartjob.users.repository.UserRepository;
import com.smartjob.users.util.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImpl Tests")
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationRulesProperties validationRulesProperties;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Spy
    private UserEntityMapperImpl userEntityMapper;

    private static List<User> mockUsers;

    private static UserRequestDto userRequestDto;

    private static final String USER_REQUEST_JSON_PATH = "src/test/resources/UserRequest.json";

    private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d).{6,}$";

    @BeforeAll
    public static void setUp() throws IOException {
        mockUsers = new ArrayList<>(List.of(new User()));
        userRequestDto = TestUtil.convertJsonToDto(USER_REQUEST_JSON_PATH, UserRequestDto.class);
    }

    @Test
    @DisplayName("Get Users Success")
    void given_validRequest_when_getUsersCalled_then_getUsers() {
        when(UserServiceImplTest.this.userRepository.findAll()).thenReturn(mockUsers);

        UserServiceImplTest.this.userService.getUsers();

        verify(UserServiceImplTest.this.userRepository).findAll();
        verify(UserServiceImplTest.this.userEntityMapper).userListToUserResponseDtoList(any());
    }

    @Nested
    @DisplayName("Create User")
    class CreateUser {
        @Test
        @DisplayName("Create User Success")
        void given_validRequest_when_createUserCalled_then_savesUser() {
            userRequestDto.setPassword("hunter2");
            when(UserServiceImplTest.this.userRepository.save(any(User.class))).thenReturn(new User());
            when(UserServiceImplTest.this.validationRulesProperties.getValidationsPasswordRegex()).thenReturn(PASSWORD_REGEX);

            UserServiceImplTest.this.userService.createUser(userRequestDto);

            verify(UserServiceImplTest.this.userRepository).save(any(User.class));
            verify(UserServiceImplTest.this.userEntityMapper).toUserResponseDto(any());
        }

        @Test
        @DisplayName("Validate Existing Email User Failed")
        void given_existingUser_when_validateExistsUserByEmail_then_shouldThrowInvalidFieldException() {
            when(UserServiceImplTest.this.userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(new User()));

            assertThrows(InvalidFieldException.class, () -> UserServiceImplTest.this.userService.createUser(userRequestDto));

            verify(UserServiceImplTest.this.userRepository).findByEmail(any(String.class));
        }

        @Test
        @DisplayName("Validate Password User Failed")
        void given_invalidUserPassword_when_validatePasswordRegex_then_shouldThrowInvalidFieldException() {
            userRequestDto.setPassword("invalid_password");
            when(UserServiceImplTest.this.validationRulesProperties.getValidationsPasswordRegex()).thenReturn(PASSWORD_REGEX);

            assertThrows(InvalidFieldException.class, () -> UserServiceImplTest.this.userService.createUser(userRequestDto));

            verify(UserServiceImplTest.this.validationRulesProperties).getValidationsPasswordRegex();
        }

    }

}
