package com.smartjob.users.model.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {

    private int status;

    private String error;

    private String message;

    private List<ErrorDto> errors;

}