package com.victorsantos.processmanagementapi.users.data;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SaveUserDataRequest {
  private String id;
  private String name;
  private String email;
  private String password;
  private String role;
}
