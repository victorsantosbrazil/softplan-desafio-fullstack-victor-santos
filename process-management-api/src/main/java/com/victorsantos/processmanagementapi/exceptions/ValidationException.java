package com.victorsantos.processmanagementapi.exceptions;

import java.util.List;

import com.victorsantos.processmanagementapi.utils.validation.ConstraintViolation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException {
  private List<ConstraintViolation> errors;
}
