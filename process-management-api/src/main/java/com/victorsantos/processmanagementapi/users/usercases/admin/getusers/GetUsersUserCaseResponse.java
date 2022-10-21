package com.victorsantos.processmanagementapi.users.usercases.admin.getusers;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetUsersUserCaseResponse {
  private String id;
  private String name;
  private String role;
}
