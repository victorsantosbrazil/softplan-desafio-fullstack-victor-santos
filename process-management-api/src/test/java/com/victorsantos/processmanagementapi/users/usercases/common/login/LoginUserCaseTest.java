package com.victorsantos.processmanagementapi.users.usercases.common.login;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.exceptions.UnauthorizedException;
import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;
import com.victorsantos.processmanagementapi.users.utils.token.JwtTokenBuilder;
import com.victorsantos.processmanagementapi.users.utils.token.TokenBuilderRequest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LoginUserCaseImpl.class)
public class LoginUserCaseTest {
  @Autowired
  private LoginUserCase userCase;

  @MockBean
  private UserDataGateway userDataGateway;

  @MockBean
  private JwtTokenBuilder jwtTokenBuilder;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @Test
  void shouldLogin() {
    String id = "kjfanbva3fa";
    String token = "jkfanvavdafdafda";
    LoginUserCaseRequest request = new LoginUserCaseRequest("test@test.com", "1234");

    UserDataResponse userDataResponse = UserDataResponse.builder()
        .id(id)
        .role("ADMIN")
        .password("fdalfdafdaba")
        .build();

    when(userDataGateway.findByEmail(request.getEmail())).thenReturn(Optional.of(userDataResponse));

    when(passwordEncoder.matches(request.getPassword(), userDataResponse.getPassword())).thenReturn(true);

    Map<String, String> claims = new HashMap<>();
    claims.put("role", userDataResponse.getRole());

    TokenBuilderRequest tokenBuilderRequest = TokenBuilderRequest.builder()
        .subject(id)
        .claims(claims)
        .build();

    when(jwtTokenBuilder.build(tokenBuilderRequest)).thenReturn(token);

    LoginUserCaseResponse expectedResponse = new LoginUserCaseResponse(token);
    LoginUserCaseResponse response = userCase.run(request);

    assertEquals(expectedResponse, response);
  }

  @Test
  void shouldNotAuthorizeWhenUserDoesNotExist() {
    LoginUserCaseRequest request = new LoginUserCaseRequest("test@test.com", "1234");

    when(userDataGateway.findByEmail(request.getEmail())).thenReturn(Optional.empty());

    assertThrows(UnauthorizedException.class, () -> userCase.run(request));

  }

  @Test
  void shouldNotAuthorizeWhenPasswordDoesNotMatch() {
    String id = "kjfanbva3fa";
    String encodedPassword = "jbvabadafa";

    LoginUserCaseRequest request = new LoginUserCaseRequest("test@test.com", "1234");

    UserDataResponse userDataResponse = UserDataResponse.builder()
        .id(id)
        .password(encodedPassword)
        .role("ADMIN")
        .build();

    when(userDataGateway.findByEmail(request.getEmail())).thenReturn(Optional.of(userDataResponse));

    when(passwordEncoder.matches(request.getPassword(), encodedPassword)).thenReturn(false);

    assertThrows(UnauthorizedException.class, () -> userCase.run(request));

  }
}
