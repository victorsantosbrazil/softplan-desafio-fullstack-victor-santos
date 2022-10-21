package com.victorsantos.processmanagementapi.users.entities;

public interface User {
  public String getId();

  public void setId(String id);

  public String getName();

  public void setName(String name);

  public String getEmail();

  public void setEmail(String email);

  public String getPassword();

  public void setPassword(String password);

  public String getRole();

  public void setRole(String role);
}
