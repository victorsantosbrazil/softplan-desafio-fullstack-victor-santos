package com.victorsantos.processmanagementapi.users.usercases.common.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginUserCaseResponse {
  private String apiToken;
}
