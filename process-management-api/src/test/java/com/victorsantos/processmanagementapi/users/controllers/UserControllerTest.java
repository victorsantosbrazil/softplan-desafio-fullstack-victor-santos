package com.victorsantos.processmanagementapi.users.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.security.users.UserRole;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCaseRequest;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCaseResponse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserController.class)
class UserControllerTest {
  @Autowired
  private UserController userController;

  @MockBean
  private CreateUserUserCase createUserUserCase;

  @Test
  void shouldCreateUser() {
    CreateUserUserCaseRequest request = CreateUserUserCaseRequest.builder()
        .name("Jonh Snow")
        .email("jonh.snow@gmail.com")
        .password("123456")
        .role(UserRole.ADMIN)
        .build();

    CreateUserUserCaseResponse expectedResponse = CreateUserUserCaseResponse.builder()
        .id("fadfalçfda")
        .name("Jonh Snow")
        .email("jonh.snow@gmail.com")
        .role(UserRole.ADMIN)
        .build();

    when(createUserUserCase.run(request)).thenReturn(expectedResponse);

    CreateUserUserCaseResponse response = userController.createUser(request);

    assertEquals(expectedResponse, response);

  }
}
