package com.victorsantos.processmanagementapi.exceptions;

import java.util.List;

import com.victorsantos.processmanagementapi.utils.validation.ConstraintViolation;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
  private List<ConstraintViolation> errors;

  public ValidationException(String msg, List<ConstraintViolation> errors) {
    super(msg);
    this.errors = errors;
  }

  public ValidationException(String msg, Throwable throwable, List<ConstraintViolation> errors) {
    super(msg, throwable);
    this.errors = errors;
  }

}
