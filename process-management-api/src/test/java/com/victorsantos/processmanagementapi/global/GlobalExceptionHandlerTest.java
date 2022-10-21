package com.victorsantos.processmanagementapi.global;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.victorsantos.processmanagementapi.common.responses.ValidationErrorResponse;
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

    ValidationErrorResponse expectedBody = new ValidationErrorResponse(LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Validation error",
        request.getServletPath(),
        violations);

    ResponseEntity<ValidationErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(expectedBody);

    assertEquals(expectedResponse, response);
  }

}
