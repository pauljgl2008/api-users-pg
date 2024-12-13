package com.smartjob.users.model.dto.user;

import com.smartjob.users.common.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UserRequestDto {

    @NotBlank(message = "El campo name es requerido")
    private String name;

    @Email(regexp = ValidationConstants.EMAIL_REGEX, message = "El campo email tiene formato inválido, formatos válidos => aaaaaaa@dominio.cl | aaaaaaa@dominio.com | aaaaaaa@dominio.org")
    @NotBlank(message = "El campo email es requerido")
    private String email;

    @NotBlank(message = "El campo password es requerido")
    private String password;

    @NotEmpty(message = "El campo phones es requerido")
    @Valid
    private List<PhoneDto> phones;
}
