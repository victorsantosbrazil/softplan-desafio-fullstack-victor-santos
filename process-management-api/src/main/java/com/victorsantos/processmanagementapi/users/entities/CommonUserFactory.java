package com.victorsantos.processmanagementapi.users.entities;

import org.springframework.stereotype.Component;

@Component
public class CommonUserFactory implements UserFactory {

  @Override
  public User create(String id, String name, String email, String password, String role) {
    return CommonUser.builder()
        .id(id)
        .name(name)
        .email(email)
        .password(password)
        .role(role)
        .build();
  }

}
