package com.victorsantos.processmanagementapi.users.usercases.admin.deleteuser;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
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
@ContextConfiguration(classes = DeleteUserUserCaseImpl.class)
class DeleteUserUserCaseTest {
  @Autowired
  private DeleteUserUserCase userCase;

  @MockBean
  private UserDataGateway userDataGateway;

  @Test
  void shouldDeleteUser() {
    String id = "çlkfdajçfjdaçfda";

    UserDataResponse userDataResponse = UserDataResponse.builder().build();

    when(userDataGateway.findById(id)).thenReturn(Optional.of(userDataResponse));

    userCase.run(id);
    verify(userDataGateway).delete(id);

  }

  @Test
  void shouldThrowNotFoundExceptionWhenUserDoesNotExist() {
    String id = "çlkfdajçfjdaçfda";

    when(userDataGateway.findById(id)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> userCase.run(id));

  }
}
