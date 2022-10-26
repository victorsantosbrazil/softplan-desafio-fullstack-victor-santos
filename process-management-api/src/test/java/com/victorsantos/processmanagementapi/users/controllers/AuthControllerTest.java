package com.victorsantos.processmanagementapi.users.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.users.usercases.common.login.LoginUserCase;
import com.victorsantos.processmanagementapi.users.usercases.common.login.LoginUserCaseRequest;
import com.victorsantos.processmanagementapi.users.usercases.common.login.LoginUserCaseResponse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AuthController.class)
public class AuthControllerTest {
  @Autowired
  private AuthController authController;

  @MockBean
  private LoginUserCase loginUserCase;

  @Test
  void shouldLoginUser() {
    LoginUserCaseRequest request = new LoginUserCaseRequest("test@test.com", "1234");

    LoginUserCaseResponse expectedResponse = new LoginUserCaseResponse("nbadfgafafda");
    when(loginUserCase.run(request)).thenReturn(expectedResponse);

    LoginUserCaseResponse response = authController.login(request);

    assertEquals(expectedResponse, response);
  }

}
