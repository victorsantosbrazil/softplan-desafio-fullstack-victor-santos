package com.victorsantos.processmanagementapi.users.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.security.users.UserRole;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommonUserFactory.class)
class CommonUserFactoryTest {

  @Autowired
  private CommonUserFactory userFactory;

  @Test
  void shouldCreateUser() {
    String id = "flafdafa";
    String name = "John Snow";
    String email = "john.snow@gmail.com";
    String password = "fadfalkfja";
    UserRole role = UserRole.ADMIN;

    User user = userFactory.create(id, name, email, password, role);
    User expectedUser = CommonUser.builder()
        .id(id)
        .name(name)
        .email(email)
        .password(password)
        .role(role)
        .build();

    assertEquals(expectedUser, user);
  }
}
