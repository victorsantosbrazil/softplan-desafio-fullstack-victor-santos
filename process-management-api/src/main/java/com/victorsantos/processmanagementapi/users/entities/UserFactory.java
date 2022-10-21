package com.victorsantos.processmanagementapi.users.entities;

import com.victorsantos.processmanagementapi.security.users.UserRole;

public interface UserFactory {
  public User create(String id, String name, String email, String password, UserRole role);
}
