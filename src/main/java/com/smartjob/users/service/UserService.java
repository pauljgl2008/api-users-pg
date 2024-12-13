package com.smartjob.users.service;

import com.smartjob.users.model.dto.user.UserRequestDto;
import com.smartjob.users.model.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getUsers();

    UserResponseDto createUser(UserRequestDto usuarioRequest);
}

