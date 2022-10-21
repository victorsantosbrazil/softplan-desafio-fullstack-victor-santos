package com.victorsantos.processmanagementapi.users.entities;

import com.victorsantos.processmanagementapi.security.users.UserRole;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommonUser implements User {
  private String id;
  private String name;
  private String email;
  private String password;
  private UserRole role;

}
