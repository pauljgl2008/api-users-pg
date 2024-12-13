package com.smartjob.users.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    @NotBlank(message = "El campo number es requerido")
    @Size(max = 36)
    private String number;

    @NotBlank(message = "El campo cityCode es requerido")
    private String cityCode;

    @NotBlank(message = "El campo countryCode es requerido")
    private String countryCode;

}
