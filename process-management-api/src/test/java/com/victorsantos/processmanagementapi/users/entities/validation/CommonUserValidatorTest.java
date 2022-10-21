package com.victorsantos.processmanagementapi.users.entities.validation;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.security.users.UserRole;
import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;
import com.victorsantos.processmanagementapi.users.entities.CommonUser;
import com.victorsantos.processmanagementapi.users.entities.User;
import com.victorsantos.processmanagementapi.utils.validation.ConstraintViolation;
import com.victorsantos.processmanagementapi.utils.validation.EmailValidator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommonUserValidator.class)
class CommonUserValidatorTest {
  @Autowired
  private UserValidator userValidator;

  @MockBean
  private EmailValidator emailValidator;

  @MockBean
  private UserDataGateway userDataGateway;

  @Test
  void shouldReturnNoConstraintViolationWhenUserIsValid() {
    User user = CommonUser.builder()
        .name("Jonh Snow")
        .email("john.snow@gmail.com")
        .password("lkfa320fafda")
        .role(UserRole.ADMIN)
        .build();

    when(emailValidator.validate(user.getEmail())).thenReturn(true);
    when(userDataGateway.findByEmail(user.getEmail())).thenReturn(Optional.empty());

    List<ConstraintViolation> violations = userValidator.validate(user);

    assertTrue(violations.isEmpty());
  }

  @Test
  void shouldReturnConstraintViolationWhenUserNameIsNull() {
    User user = CommonUser.builder().build();
    List<ConstraintViolation> violations = userValidator.validate(user);

    ConstraintViolation violation = new ConstraintViolation("name", "name required");
    assertTrue(violations.contains(violation));
  }

  @Test
  void shouldReturnConstraintViolationWhenUserNameIsEmpty() {
    User user = CommonUser.builder().name("").build();
    List<ConstraintViolation> violations = userValidator.validate(user);

    ConstraintViolation violation = new ConstraintViolation("name", "name required");
    assertTrue(violations.contains(violation));
  }

  @Test
  void shouldReturnConstraintViolationWhenUserEmailIsNull() {
    User user = CommonUser.builder().build();
    List<ConstraintViolation> violations = userValidator.validate(user);

    ConstraintViolation violation = new ConstraintViolation("email", "email required");
    assertTrue(violations.contains(violation));
  }

  @Test
  void shouldReturnConstraintViolationWhenUserEmailIsEmpty() {
    User user = CommonUser.builder().email("").build();
    List<ConstraintViolation> violations = userValidator.validate(user);

    ConstraintViolation violation = new ConstraintViolation("email", "email required");
    assertTrue(violations.contains(violation));
  }

  @Test
  void shouldReturnConstraintViolationWhenUserEmailIsInvalid() {
    User user = CommonUser.builder().email("test.test#fefa.com").build();

    when(emailValidator.validate(user.getEmail())).thenReturn(false);

    List<ConstraintViolation> violations = userValidator.validate(user);

    ConstraintViolation violation = new ConstraintViolation("email", "email is invalid");
    assertTrue(violations.contains(violation));
  }

  @Test
  void shouldReturnConstraintViolationWhenUserEmailIsAlreadyInUse() {
    User user = CommonUser.builder().email("test@test.com").build();

    when(emailValidator.validate(user.getEmail())).thenReturn(true);

    UserDataResponse mockUserDataResponse = UserDataResponse.builder().id("fafdafdali3fa").build();
    when(userDataGateway.findByEmail(user.getEmail())).thenReturn(Optional.of(mockUserDataResponse));

    List<ConstraintViolation> violations = userValidator.validate(user);

    ConstraintViolation violation = new ConstraintViolation("email", "email is already in use");
    assertTrue(violations.contains(violation));
  }

  @Test
  void shouldReturnConstraintViolationWhenUserPasswordIsNull() {
    User user = CommonUser.builder().build();
    List<ConstraintViolation> violations = userValidator.validate(user);

    ConstraintViolation violation = new ConstraintViolation("password", "password required");
    assertTrue(violations.contains(violation));
  }

  @Test
  void shouldReturnConstraintViolationWhenUserPasswordIsEmpty() {
    User user = CommonUser.builder().password("").build();
    List<ConstraintViolation> violations = userValidator.validate(user);

    ConstraintViolation violation = new ConstraintViolation("password", "password required");
    assertTrue(violations.contains(violation));
  }

  @Test
  void shouldReturnConstraintViolationWhenUserRoleIsNull() {
    User user = CommonUser.builder().build();
    List<ConstraintViolation> violations = userValidator.validate(user);

    ConstraintViolation violation = new ConstraintViolation("role", "role required");
    assertTrue(violations.contains(violation));
  }
}
