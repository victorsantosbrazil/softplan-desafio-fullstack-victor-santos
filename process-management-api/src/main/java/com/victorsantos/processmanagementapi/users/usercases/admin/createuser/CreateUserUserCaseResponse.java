package com.victorsantos.processmanagementapi.users.usercases.admin.createuser;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateUserUserCaseResponse {
  private String id;
  private String name;
  private String email;
  private String role;
}
