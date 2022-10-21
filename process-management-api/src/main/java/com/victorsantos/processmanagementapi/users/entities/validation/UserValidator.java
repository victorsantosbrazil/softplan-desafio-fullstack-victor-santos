package com.victorsantos.processmanagementapi.users.entities.validation;

import java.util.List;

import com.victorsantos.processmanagementapi.users.entities.User;
import com.victorsantos.processmanagementapi.utils.validation.ConstraintViolation;

public interface UserValidator {
  public List<ConstraintViolation> validate(User user);
}
