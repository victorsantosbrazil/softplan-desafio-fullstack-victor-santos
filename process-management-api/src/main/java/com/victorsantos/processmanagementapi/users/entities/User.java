package com.victorsantos.processmanagementapi.users.entities;

import com.victorsantos.processmanagementapi.security.users.UserRole;

public interface User {
  public String getId();

  public void setId(String id);

  public String getName();

  public void setName(String name);

  public String getEmail();

  public void setEmail(String email);

  public String getPassword();

  public void setPassword(String password);

  public UserRole getRole();

  public void setRole(UserRole role);
}
