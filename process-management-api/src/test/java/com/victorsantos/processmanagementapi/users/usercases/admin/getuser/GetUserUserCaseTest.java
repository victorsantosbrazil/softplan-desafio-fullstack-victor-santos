package com.victorsantos.processmanagementapi.users.usercases.admin.getuser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.exceptions.NotFoundException;
import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GetUserUserCaseImpl.class)
class GetUserUserCaseTest {
  @Autowired
  private GetUserUserCase userCase;

  @MockBean
  private UserDataGateway userDataGateway;

  @Test
  void shouldReturnUser() {
    String id = "lfadfdafda";

    UserDataResponse userDataResponse = UserDataResponse.builder()
        .id(id)
        .name("Victor Santos")
        .email("victor@test.com")
        .password("faldkfdaçfdaf")
        .role("ADMIN")
        .build();

    when(userDataGateway.findById(id)).thenReturn(Optional.of(userDataResponse));

    GetUserUserCaseResponse response = userCase.run(id);

    GetUserUserCaseResponse expectedResponse = GetUserUserCaseResponse.builder()
        .id(id)
        .name(userDataResponse.getName())
        .email(userDataResponse.getEmail())
        .role(userDataResponse.getRole())
        .build();

    assertEquals(expectedResponse, response);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenUserIsNotFound() {
    String id = "lfadfdafda";

    when(userDataGateway.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> userCase.run(id));
  }
}
