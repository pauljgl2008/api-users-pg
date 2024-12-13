package com.smartjob.users.model.dto.auth;

import com.smartjob.users.common.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthLoginRequestDto {

    @Email(regexp = ValidationConstants.EMAIL_REGEX, message = "El campo email tiene formato inválido, formatos válidos => aaaaaaa@dominio.cl | aaaaaaa@dominio.com | aaaaaaa@dominio.org")
    @NotBlank(message = "El campo email es requerido")
    private String email;

    @NotBlank(message = "El campo password es requerido")
    private String password;

}