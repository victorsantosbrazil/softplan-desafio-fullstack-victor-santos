package com.victorsantos.processmanagementapi.global;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.victorsantos.processmanagementapi.common.responses.ValidationErrorResponse;
import com.victorsantos.processmanagementapi.exceptions.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationException(ValidationException e,
      HttpServletRequest request) {

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    ValidationErrorResponse response = new ValidationErrorResponse(
        LocalDateTime.now(),
        httpStatus.value(),
        e.getMessage(),
        request.getServletPath(),
        e.getErrors());

    return ResponseEntity.status(httpStatus).body(response);
  }

}
