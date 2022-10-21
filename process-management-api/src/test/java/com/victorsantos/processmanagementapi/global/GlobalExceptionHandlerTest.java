package com.victorsantos.processmanagementapi.global;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.common.responses.ErrorResponse;
import com.victorsantos.processmanagementapi.common.responses.ValidationErrorResponse;
import com.victorsantos.processmanagementapi.exceptions.NotFoundException;
import com.victorsantos.processmanagementapi.exceptions.ValidationException;
import com.victorsantos.processmanagementapi.utils.validation.ConstraintViolation;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

  @Autowired
  private GlobalExceptionHandler exceptionHandler;

  @Test
  void whenHandleValidationExceptionThenReturnValidationErrorResponse() {
    MockHttpServletRequest request = new MockHttpServletRequest();

    List<ConstraintViolation> violations = new ArrayList<>();
    violations.add(new ConstraintViolation("name", "name required"));
    violations.add(new ConstraintViolation("cpf", "cpf required"));

    ValidationException exception = new ValidationException("Validation Exception", violations);

    ResponseEntity<ValidationErrorResponse> response = exceptionHandler.handleValidationException(exception, request);

    ValidationErrorResponse responseBody = response.getBody();

    ValidationErrorResponse expectedBody = new ValidationErrorResponse(LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        exception.getMessage(),
        request.getServletPath(),
        violations);

    ResponseEntity<ValidationErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(expectedBody);

    assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    assertNotNull(responseBody);
    assertEquals(expectedBody.getMessage(), responseBody.getMessage());
    assertEquals(expectedBody.getStatus(), responseBody.getStatus());
    assertEquals(expectedBody.getPath(), responseBody.getPath());
    assertEquals(expectedBody.getViolations(), responseBody.getViolations());
  }

  @Test
  void whenHandleNotFoundExceptionThenReturnErrorResponse() {
    MockHttpServletRequest request = new MockHttpServletRequest();

    NotFoundException exception = new NotFoundException("Resource not found");

    ResponseEntity<ErrorResponse> response = exceptionHandler.handleNotFoundException(exception, request);

    ErrorResponse responseBody = response.getBody();

    ErrorResponse expectedBody = new ErrorResponse(LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        exception.getMessage(),
        request.getServletPath());

    ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(expectedBody);

    assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    assertNotNull(responseBody);
    assertEquals(expectedBody.getMessage(), responseBody.getMessage());
    assertEquals(expectedBody.getPath(), responseBody.getPath());
  }
}
