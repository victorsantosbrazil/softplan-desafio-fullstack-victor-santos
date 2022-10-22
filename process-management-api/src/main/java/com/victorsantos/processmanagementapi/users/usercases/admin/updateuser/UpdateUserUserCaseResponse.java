package com.victorsantos.processmanagementapi.users.usercases.admin.updateuser;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUserUserCaseResponse {
  private String id;
  private String name;
  private String email;
  private String role;
}
