package com.smartjob.users.service;

import com.smartjob.users.common.ValidationConstants;
import com.smartjob.users.common.ValidationRulesProperties;
import com.smartjob.users.exception.InvalidFieldException;
import com.smartjob.users.mapper.UserEntityMapper;
import com.smartjob.users.model.dto.user.UserRequestDto;
import com.smartjob.users.model.dto.user.UserResponseDto;
import com.smartjob.users.model.entity.User;
import com.smartjob.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@EnableConfigurationProperties(ValidationRulesProperties.class)
@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final ValidationRulesProperties validationRulesProperties;
    private final UserEntityMapper userEntityMapper;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public List<UserResponseDto> getUsers() {
        return this.userEntityMapper.userListToUserResponseDtoList(this.userRepository.findAll());
    }

    public UserResponseDto createUser(UserRequestDto userRequest) {
        this.validateExistsUserByEmail(userRequest.getEmail());
        this.validatePasswordRegex(userRequest.getPassword());
        User newUser = this.userEntityMapper.fromUserRequestDto(userRequest);
        newUser.setPassword(this.encoder.encode(userRequest.getPassword()));
        newUser.setCreated(Instant.now());
        newUser.setModified(Instant.now());
        newUser.setLastLogin(Instant.now());
        newUser.setToken(this.authService.generateToken(userRequest.getEmail()));
        newUser.setIsActive(true);
        User userCreated = this.userRepository.save(newUser);
        return this.userEntityMapper.toUserResponseDto(userCreated);
    }

    private void validateExistsUserByEmail(String email) {
        this.userRepository.findByEmail(email)
                .ifPresent(userFound -> {
                    throw new InvalidFieldException(HttpStatus.BAD_REQUEST, "email", userFound.getEmail(),
                            ValidationConstants.EMAIL_EXISTS_MESSAGE);
                });
    }

    private void validatePasswordRegex(String password) {
        if (!password.matches(validationRulesProperties.getValidationsPasswordRegex())) {
            throw new InvalidFieldException(HttpStatus.BAD_REQUEST, "password", password, "El campo password tiene formato inválido");
        }
    }

}