package com.victorsantos.processmanagementapi.utils.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RFC5322EmailValidator.class)
class RFC5322EmailValidatorTest {
  @Autowired
  private EmailValidator emailValidator;

  @Test
  void shouldReturnTrueWhenEmailIsValid() {
    String email = "test@test.com";
    Boolean isValid = emailValidator.validate(email);
    assertTrue(isValid);
  }

  @Test
  void shouldReturnFalseWhenEmailIsInvalid() {
    String email = "test#test.com";
    Boolean isValid = emailValidator.validate(email);
    assertFalse(isValid);
  }
}
