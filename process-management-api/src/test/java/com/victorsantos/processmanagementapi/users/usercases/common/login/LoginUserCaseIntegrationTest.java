package com.victorsantos.processmanagementapi.users.usercases.common.login;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorsantos.processmanagementapi.common.responses.ErrorType;
import com.victorsantos.processmanagementapi.initializers.PostgreSQLContainerInitializer;
import com.victorsantos.processmanagementapi.users.data.repositories.UserJpaRepository;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCaseRequest;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = { PostgreSQLContainerInitializer.class })
@AutoConfigureMockMvc
public class LoginUserCaseIntegrationTest {
  @Autowired
  private CreateUserUserCase createUserUserCase;

  @Autowired
  private UserJpaRepository userJpaRepository;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @AfterEach
  public void tearDown() {
    userJpaRepository.deleteAll();
  }

  @Test
  void shouldLogin() throws Exception {
    String email = "victor@test.com";
    String password = "1234";

    CreateUserUserCaseRequest createUserRequest = CreateUserUserCaseRequest.builder()
        .name("Victor Souza")
        .email(email)
        .password(password)
        .role("ADMIN")
        .build();

    createUserUserCase.run(createUserRequest);

    LoginUserCaseRequest loginRequest = new LoginUserCaseRequest(email, password);

    mockMvc.perform(
        post("/auth/basic")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.apiToken").isNotEmpty());
  }

  @Test
  void shouldNotAuthorizeWhenPasswordDoesNotMatch() throws JsonProcessingException, Exception {
    String email = "victor@test.com";
    String password = "1234";

    CreateUserUserCaseRequest createUserRequest = CreateUserUserCaseRequest.builder()
        .name("Victor Souza")
        .email(email)
        .password(password)
        .role("ADMIN")
        .build();

    createUserUserCase.run(createUserRequest);

    LoginUserCaseRequest loginRequest = new LoginUserCaseRequest(email, "5678");

    mockMvc.perform(
        post("/auth/basic")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.value()))
        .andExpect(jsonPath("$.error").value(ErrorType.UNAUTHORIZED.toString()))
        .andExpect(jsonPath("$.message").value("Password is invalid"));
  }

  @Test
  void shouldNotAuthorizeWhenThereIsNoUserWithEmail() throws JsonProcessingException, Exception {
    String email = "victor@test.com";

    LoginUserCaseRequest loginRequest = new LoginUserCaseRequest(email, "5678");

    mockMvc.perform(
        post("/auth/basic")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.value()))
        .andExpect(jsonPath("$.error").value(ErrorType.UNAUTHORIZED.toString()))
        .andExpect(jsonPath("$.message").value("Email not found"));
  }
}
