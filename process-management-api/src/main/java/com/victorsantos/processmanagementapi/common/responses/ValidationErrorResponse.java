package com.victorsantos.processmanagementapi.common.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.victorsantos.processmanagementapi.utils.validation.ConstraintViolation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ValidationErrorResponse extends ErrorResponse {
  List<ConstraintViolation> violations;

  public ValidationErrorResponse(LocalDateTime timestamp, Integer status, String message, String path,
      List<ConstraintViolation> violations) {
    super(timestamp, status, message, path);
    this.violations = violations;
  }

}
