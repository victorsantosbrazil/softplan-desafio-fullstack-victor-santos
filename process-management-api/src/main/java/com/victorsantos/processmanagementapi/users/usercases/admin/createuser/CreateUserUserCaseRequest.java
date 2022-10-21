package com.victorsantos.processmanagementapi.users.usercases.admin.createuser;

import com.victorsantos.processmanagementapi.security.users.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserUserCaseRequest {
  private String name;
  private String email;
  private String password;
  private UserRole role;
}
