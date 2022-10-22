package com.victorsantos.processmanagementapi.users.usercases.admin.updateuser;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorsantos.processmanagementapi.initializers.PostgreSQLContainerInitializer;
import com.victorsantos.processmanagementapi.users.data.repositories.UserJpaRepository;
import com.victorsantos.processmanagementapi.users.data.repositories.models.UserDataModel;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = { PostgreSQLContainerInitializer.class })
@AutoConfigureMockMvc
class UpdateUserCaseIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserJpaRepository userJpaRepository;

  @AfterEach
  public void setup() {
    userJpaRepository.deleteAll();
  }

  @Test
  void shouldUpdateUser() throws JsonProcessingException, Exception {
    UserDataModel currentUserDataModel = UserDataModel.builder()
        .name("Jonh Snow")
        .email("jonh.snow@gmail.com")
        .password("çlkfjdadfdassfd")
        .role("ADMIN")
        .build();

    currentUserDataModel = userJpaRepository.save(currentUserDataModel);

    String id = currentUserDataModel.getId().toString();

    UpdateUserUserCaseRequest request = UpdateUserUserCaseRequest.builder()
        .name("Jonh das Neves")
        .email("joao.neves@gmail.com")
        .role("PROCESS_SCREENER")
        .build();

    mockMvc.perform(
        patch("/users/" + id)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.name").value(request.getName()))
        .andExpect(jsonPath("$.email").value(request.getEmail()))
        .andExpect(jsonPath("$.role").value(request.getRole()));

    UserDataModel userData = userJpaRepository.findById(currentUserDataModel.getId()).get();

    assertEquals(request.getName(), userData.getName());
    assertEquals(request.getEmail(), userData.getEmail());
    assertEquals(currentUserDataModel.getPassword(), userData.getPassword());
    assertEquals(request.getRole(), userData.getRole());

  }

  @Test
  void whenUserDoesNotExistThenNotFoundException() throws JsonProcessingException, Exception {

    String id = UUID.randomUUID().toString();

    UpdateUserUserCaseRequest request = UpdateUserUserCaseRequest.builder()
        .name("Jonh das Neves")
        .email("joao.neves@gmail.com")
        .role("PROCESS_SCREENER")
        .build();

    mockMvc.perform(
        patch("/users/" + id)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("User not found with id " + id))
        .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.timestamp").isNotEmpty());

  }

  @Test
  void whenRequestWithInvalidFieldsThenReturnValidationErrorResponse() throws JsonProcessingException, Exception {

    UserDataModel currentUserDataModel = UserDataModel.builder()
        .name("Jonh Snow")
        .email("jonh.snow@gmail.com")
        .password("çlkfjdadfdassfd")
        .role("ADMIN")
        .build();

    currentUserDataModel = userJpaRepository.save(currentUserDataModel);

    String id = currentUserDataModel.getId().toString();

    UpdateUserUserCaseRequest request = UpdateUserUserCaseRequest.builder()
        .name("")
        .email("jonh.snow#gmail.com")
        .role("ADMIN")
        .build();

    mockMvc.perform(
        patch("/users/" + id)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.message").value("Validation error"))
        .andExpect(jsonPath("$.violations[0].propertyPath").value("name"))
        .andExpect(jsonPath("$.violations[0].errorMessage").value("name required"))
        .andExpect(jsonPath("$.violations[1].propertyPath").value("email"))
        .andExpect(jsonPath("$.violations[1].errorMessage").value("email is invalid"));

  }
}
