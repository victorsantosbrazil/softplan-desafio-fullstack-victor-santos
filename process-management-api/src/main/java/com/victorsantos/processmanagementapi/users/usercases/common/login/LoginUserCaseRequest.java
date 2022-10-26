package com.victorsantos.processmanagementapi.users.usercases.common.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUserCaseRequest {
  private String email;
  private String password;
}
