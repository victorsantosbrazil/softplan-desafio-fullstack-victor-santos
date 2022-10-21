package com.victorsantos.processmanagementapi.users.entities;

public interface UserFactory {
  public User create(String id, String name, String email, String password, String role);
}
