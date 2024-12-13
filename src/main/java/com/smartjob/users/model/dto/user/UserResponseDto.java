package com.smartjob.users.model.dto.user;

import lombok.Data;

@Data
public class UserResponseDto {

    private String id;

    private String created;

    private String modified;

    private String lastLogin;

    private String token;

    private String isActive;

}
