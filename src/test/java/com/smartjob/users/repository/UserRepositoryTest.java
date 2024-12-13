package com.smartjob.users.repository;

import com.smartjob.users.model.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserRepository Tests")
class UserRepositoryTest {

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    private User user = new User();

    @Test
    @DisplayName("Save User Success")
    void given_validUser_when_saveCalled_then_returnsUser() {
        when(UserRepositoryTest.this.userJpaRepository.save(any(User.class))).thenReturn(this.user);

        UserRepositoryTest.this.userRepository.save(this.user);

        verify(UserRepositoryTest.this.userJpaRepository).save(any(User.class));
    }

    @Test
    @DisplayName("FindBy Email User Success")
    void given_validUser_when_findByEmailCalled_then_returnsUser() {
        when(UserRepositoryTest.this.userJpaRepository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(this.user));

        UserRepositoryTest.this.userRepository.findByEmail("test@test.com");

        verify(UserRepositoryTest.this.userJpaRepository).findByEmail("test@test.com");
    }

    @Test
    @DisplayName("FindAll Users Success")
    void given_requestGetUsers_when_findAllCalled_then_returnsListOfUsers() {
        List<User> mockUsers = new ArrayList<>(List.of(new User()));
        when(UserRepositoryTest.this.userJpaRepository.findAll()).thenReturn(mockUsers);

        UserRepositoryTest.this.userRepository.findAll();

        verify(UserRepositoryTest.this.userJpaRepository).findAll();
    }

}
