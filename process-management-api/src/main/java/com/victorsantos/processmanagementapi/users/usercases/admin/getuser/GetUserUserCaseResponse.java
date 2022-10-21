package com.victorsantos.processmanagementapi.users.usercases.admin.getuser;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetUserUserCaseResponse {
  private String id;
  private String name;
  private String email;
  private String role;
}
