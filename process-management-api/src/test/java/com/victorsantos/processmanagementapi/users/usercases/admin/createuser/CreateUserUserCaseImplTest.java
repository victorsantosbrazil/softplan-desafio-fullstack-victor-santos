package com.victorsantos.processmanagementapi.users.usercases.admin.createuser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.exceptions.ValidationException;
import com.victorsantos.processmanagementapi.security.users.UserRole;
import com.victorsantos.processmanagementapi.users.data.SaveUserDataRequest;
import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;
import com.victorsantos.processmanagementapi.users.entities.CommonUser;
import com.victorsantos.processmanagementapi.users.entities.User;
import com.victorsantos.processmanagementapi.users.entities.UserFactory;
import com.victorsantos.processmanagementapi.users.entities.validation.UserValidator;
import com.victorsantos.processmanagementapi.utils.validation.ConstraintViolation;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CreateUserUserCaseImpl.class)
class CreateUserUserCaseImplTest {
  @Autowired
  private CreateUserUserCase userCase;

  @MockBean
  private UserDataGateway userDataGateway;

  @MockBean
  private UserFactory userFactory;

  @MockBean
  private UserValidator userValidator;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @Test
  void shouldCreateUser() {
    String mockId = "fl3eifafda";
    String encodedPassword = "fadfaçljnvbaçvdaçfkda";

    CreateUserUserCaseRequest request = CreateUserUserCaseRequest.builder()
        .name("Jonh Snow")
        .email("jonh.snow@gmail.com")
        .password("123456")
        .role(UserRole.ADMIN)
        .build();

    SaveUserDataRequest expectedSavedData = SaveUserDataRequest.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(encodedPassword)
        .role(request.getRole())
        .build();

    UserDataResponse mockDataGatewayResponse = UserDataResponse.builder()
        .id(mockId)
        .name(request.getName())
        .email(request.getEmail())
        .password(encodedPassword)
        .role(request.getRole())
        .build();

    CreateUserUserCaseResponse expectedResponse = CreateUserUserCaseResponse.builder()
        .id(mockId)
        .name(request.getName())
        .email(request.getEmail())
        .role(request.getRole())
        .build();

    when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);

    ArgumentCaptor<SaveUserDataRequest> dataGatewayRequestCaptor = ArgumentCaptor
        .forClass(SaveUserDataRequest.class);
    when(userDataGateway.save(dataGatewayRequestCaptor.capture())).thenReturn(mockDataGatewayResponse);

    CreateUserUserCaseResponse response = userCase.run(request);

    assertEquals(expectedSavedData, dataGatewayRequestCaptor.getValue());
    assertEquals(expectedResponse, response);
  }

  @Test
  void shouldThrowValidationExceptionWhenDataIsInvalid() {

    CreateUserUserCaseRequest request = CreateUserUserCaseRequest.builder()
        .name("")
        .email("jonh.snow@gmail.com")
        .password("123456")
        .role(UserRole.ADMIN)
        .build();

    User user = CommonUser.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(request.getPassword())
        .role(request.getRole())
        .build();

    when(userFactory.create(null, request.getName(), request.getEmail(), request.getPassword(), request.getRole()))
        .thenReturn(user);

    List<ConstraintViolation> violations = new ArrayList<>();
    violations.add(new ConstraintViolation("name", "Name is empty"));

    when(userValidator.validate(user)).thenReturn(violations);

    assertThrows(ValidationException.class, () -> userCase.run(request));
  }
}
