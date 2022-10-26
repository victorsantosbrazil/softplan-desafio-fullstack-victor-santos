package com.victorsantos.processmanagementapi.users.usercases.common.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.victorsantos.processmanagementapi.exceptions.UnauthorizedException;
import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;
import com.victorsantos.processmanagementapi.users.utils.token.JwtTokenBuilder;
import com.victorsantos.processmanagementapi.users.utils.token.TokenBuilderRequest;

@Component
public class LoginUserCaseImpl implements LoginUserCase {
  @Autowired
  private UserDataGateway userDataGateway;

  @Autowired
  private JwtTokenBuilder jwtTokenBuilder;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public LoginUserCaseResponse run(LoginUserCaseRequest request) {
    UserDataResponse userData = findUserByEmail(request.getEmail());

    boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), userData.getPassword());
    if (!isPasswordMatch) {
      throw new UnauthorizedException("Password is invalid");
    }

    String token = buildToken(userData);
    return LoginUserCaseResponse.builder()
        .apiToken(token)
        .build();
  }

  private UserDataResponse findUserByEmail(String email) {
    return userDataGateway.findByEmail(email).orElseThrow(() -> new UnauthorizedException("Email not found"));
  }

  private String buildToken(UserDataResponse userData) {
    Map<String, String> tokenClaims = new HashMap<>();
    tokenClaims.put("role", userData.getRole());

    TokenBuilderRequest tokenBuilderRequest = TokenBuilderRequest.builder()
        .subject(userData.getId())
        .claims(tokenClaims)
        .build();

    return jwtTokenBuilder.build(tokenBuilderRequest);
  }

}
