package com.victorsantos.processmanagementapi.users.entities.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victorsantos.processmanagementapi.security.users.UserRole;
import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;
import com.victorsantos.processmanagementapi.users.entities.User;
import com.victorsantos.processmanagementapi.utils.validation.ConstraintViolation;
import com.victorsantos.processmanagementapi.utils.validation.EmailValidator;

@Component
public class CommonUserValidator implements UserValidator {

  @Autowired
  private EmailValidator emailValidator;

  @Autowired
  private UserDataGateway userDataGateway;

  @Override
  public List<ConstraintViolation> validate(User user) {
    List<ConstraintViolation> nameViolations = validateName(user.getName());
    List<ConstraintViolation> emailViolations = validateEmail(user.getEmail());
    List<ConstraintViolation> passwordViolations = validatePassword(user.getPassword());
    List<ConstraintViolation> roleViolations = validateRole(user.getRole());

    List<ConstraintViolation> allViolations = new ArrayList<>();
    allViolations.addAll(nameViolations);
    allViolations.addAll(emailViolations);
    allViolations.addAll(passwordViolations);
    allViolations.addAll(roleViolations);
    return allViolations;
  }

  private List<ConstraintViolation> validateName(String name) {
    List<ConstraintViolation> violations = new ArrayList<>();

    if (name == null || name.isEmpty()) {
      ConstraintViolation violation = new ConstraintViolation("name", "name required");
      violations.add(violation);
    }

    return violations;
  }

  private List<ConstraintViolation> validateEmail(String email) {
    List<ConstraintViolation> violations = new ArrayList<>();

    if (email == null || email.isEmpty()) {
      ConstraintViolation violation = new ConstraintViolation("email", "email required");
      violations.add(violation);
      return violations;
    }

    Boolean isEmailValid = emailValidator.validate(email);
    if (!isEmailValid) {
      ConstraintViolation violation = new ConstraintViolation("email", "email is invalid");
      violations.add(violation);
      return violations;
    }

    UserDataResponse userDataResponse = userDataGateway.findByEmail(email).orElse(null);
    if (userDataResponse != null) {
      ConstraintViolation violation = new ConstraintViolation("email", "email is already in use");
      violations.add(violation);
    }

    return violations;
  }

  private List<ConstraintViolation> validatePassword(String password) {
    List<ConstraintViolation> violations = new ArrayList<>();

    if (password == null || password.isEmpty()) {
      ConstraintViolation violation = new ConstraintViolation("password", "password required");
      violations.add(violation);
    }

    return violations;
  }

  private List<ConstraintViolation> validateRole(String role) {
    List<ConstraintViolation> violations = new ArrayList<>();

    if (role == null) {
      ConstraintViolation violation = new ConstraintViolation("role", "role required");
      violations.add(violation);
      return violations;
    }

    List<String> roles = Arrays.stream(UserRole.values()).map(UserRole::name).collect(Collectors.toList());

    boolean containsRole = roles.contains(role);
    if (!containsRole) {
      ConstraintViolation violation = new ConstraintViolation("role",
          "role is invalid. Possible roles: " + roles);

      violations.add(violation);
    }

    return violations;
  }

}
