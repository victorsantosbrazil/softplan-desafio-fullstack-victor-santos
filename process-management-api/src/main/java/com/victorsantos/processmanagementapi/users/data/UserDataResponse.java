package com.victorsantos.processmanagementapi.users.data;

import com.victorsantos.processmanagementapi.security.users.UserRole;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDataResponse {
  private String id;
  private String name;
  private String email;
  private String password;
  private UserRole role;
}
