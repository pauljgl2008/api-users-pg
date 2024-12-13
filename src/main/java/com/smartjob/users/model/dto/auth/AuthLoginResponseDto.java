package com.smartjob.users.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a response containing an authentication token.
 * This DTO is typically used to return an access token to the client after a successful login.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginResponseDto {

    /**
     * The authentication token issued for the user.
     */
    private String token;

}

