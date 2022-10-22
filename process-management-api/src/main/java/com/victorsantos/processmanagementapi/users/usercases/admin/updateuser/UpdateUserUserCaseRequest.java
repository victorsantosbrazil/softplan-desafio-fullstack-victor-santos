package com.victorsantos.processmanagementapi.users.usercases.admin.updateuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateUserUserCaseRequest {
  private String id;
  private String name;
  private String email;
  private String role;
}
