package com.victorsantos.processmanagementapi.utils.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConstraintViolation {
  private String propertyPath;
  private String errorMessage;
}
