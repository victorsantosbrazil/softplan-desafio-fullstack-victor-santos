package com.victorsantos.processmanagementapi.utils.validation;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class RFC5322EmailValidator implements EmailValidator {

  @Override
  public boolean validate(String email) {
    String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    return Pattern.compile(regexPattern)
        .matcher(email)
        .matches();
  }

}
