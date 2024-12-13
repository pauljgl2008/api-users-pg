package com.smartjob.users.exception;

import com.smartjob.users.model.dto.error.ErrorDto;
import com.smartjob.users.model.dto.error.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  @NonNull final HttpHeaders headers,
                                                                  @NonNull final HttpStatusCode status, @NonNull final WebRequest request) {

        return this.retrieveBadRequest(ex.getBindingResult().getFieldErrors());
    }

    @ExceptionHandler(InvalidFieldException.class)
    public final ResponseEntity<Object> serviceExceptionHandler(final InvalidFieldException ex) {
        return this.retrieveBadRequest(List.of(this.buildFieldError(ex.getFieldName(), ex.getRejectedValue(), ex.getMessage())));
    }

    private ResponseEntity<Object> retrieveBadRequest(List<FieldError> fieldErrors) {
        List<ErrorDto> errors = fieldErrors.stream()
                .map(err -> new ErrorDto(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                .toList();

        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed",
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }


    private FieldError buildFieldError(final String fieldName, final String rejectedValue, final String message) {
        return new FieldError("", fieldName, rejectedValue, true,
                null, null, message);
    }
}
