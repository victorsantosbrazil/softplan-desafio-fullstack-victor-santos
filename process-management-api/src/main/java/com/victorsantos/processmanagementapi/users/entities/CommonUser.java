package com.victorsantos.processmanagementapi.users.entities;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommonUser implements User {
  private String id;
  private String name;
  private String email;
  private String password;
  private String role;

}
