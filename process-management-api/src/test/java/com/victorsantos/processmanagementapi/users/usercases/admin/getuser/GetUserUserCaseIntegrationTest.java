package com.victorsantos.processmanagementapi.users.usercases.admin.getuser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.victorsantos.processmanagementapi.initializers.PostgreSQLContainerInitializer;
import com.victorsantos.processmanagementapi.users.data.repositories.UserJpaRepository;
import com.victorsantos.processmanagementapi.users.data.repositories.models.UserDataModel;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = { PostgreSQLContainerInitializer.class })
@AutoConfigureMockMvc
class GetUserUserCaseIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserJpaRepository userJpaRepository;

  @AfterEach
  public void tearDown() {
    userJpaRepository.deleteAll();
  }

  @Test
  void shouldGetUser() throws JsonProcessingException, Exception {

    UserDataModel userModel = UserDataModel.builder()
        .name("John")
        .email("john@test.com")
        .password(UUID.randomUUID().toString())
        .role("ADMIN")
        .build();

    userModel = userJpaRepository.save(userModel);

    String id = userModel.getId().toString();
    mockMvc.perform(get("/users/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(userModel.getId().toString()))
        .andExpect(jsonPath("$.name").value(userModel.getName()))
        .andExpect(jsonPath("$.email").value(userModel.getEmail()))
        .andExpect(jsonPath("$.role").value(userModel.getRole()));
  }

  @Test
  void shouldReturnErrorWhenUserDoesNotExist() throws JsonProcessingException, Exception {

    String id = UUID.randomUUID().toString();

    mockMvc.perform(get("/users/" + id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("User not found with id " + id))
        .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

}
