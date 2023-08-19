package com.security.board.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponse> handleResponseCodeExceptions(CustomException ex) {
        log.warn("W={}, message={}", ex.getExceptionCodes().getErrorCode(),
                ex.getExceptionCodes().getMessage());
        return ResponseEntity.status(ex.getStatus()).body(ex.getResponse());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomResponse> handleMissingServletRequestParameterExceptions(ConstraintViolationException ex) {
        log.error(ex.getMessage());
        var status = ExceptionCodes.CPF_INVALIDO.getStatus();
        return ResponseEntity.status(status)
                .body(new CustomResponse(status,
                        ExceptionCodes.CPF_INVALIDO.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        if (ex.getCause() instanceof JsonMappingException causes) {
            String message = "Invalid type for parameter(s): ";
            message += causes.getPath().stream().map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("; "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CustomResponse(HttpStatus.BAD_REQUEST.value(), message));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomResponse(HttpStatus.BAD_REQUEST.value(),"Error decrypting payload"));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CustomResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        var status = ExceptionCodes.TOKEN_INVALID.getStatus();
        var message = ExceptionCodes.TOKEN_INVALID.getMessage();
        log.warn("W={}, message={}", status, message);
        return ResponseEntity.status(status)
                .body(new CustomResponse(status, message));
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<CustomResponse> handleJWTVerificationException(JWTVerificationException ex) {
        var status = ExceptionCodes.TOKEN_INVALID.getStatus();
        var message = ExceptionCodes.TOKEN_INVALID.getMessage();
        log.warn("W={}, message={}", status, message);
        return ResponseEntity.status(status)
                .body(new CustomResponse(status, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> handleGenericExceptions(Exception ex) {
        log.error(ex.getMessage());
        var response = new CustomException(ExceptionCodes.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(response.getStatus()).body(response.getResponse());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return handleValidationExceptions(ex.getBindingResult());
    }

    private ResponseEntity<CustomResponse> handleValidationExceptions(BindingResult bindingResult) {
        String message = bindingResult.getAllErrors().stream().map(error -> {
            if (error.getClass().isAssignableFrom(FieldError.class) || error.getClass().getSuperclass().isAssignableFrom(FieldError.class)) {
                return "[" + ((FieldError) error).getField() + "]: " + error.getDefaultMessage();
            }

            return error.getDefaultMessage();
        }).collect(Collectors.joining("; "));
        String source = Objects.requireNonNull(bindingResult.getTarget()).getClass().getSimpleName();
        log.error("{}: {}", source, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomResponse(HttpStatus.BAD_REQUEST.value(), message));
    }

}
