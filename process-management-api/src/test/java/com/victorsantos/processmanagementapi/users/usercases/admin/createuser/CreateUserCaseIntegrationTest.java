package com.victorsantos.processmanagementapi.users.usercases.admin.createuser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorsantos.processmanagementapi.security.users.UserRole;
import com.victorsantos.processmanagementapi.users.data.repositories.UserJpaRepository;
import com.victorsantos.processmanagementapi.users.data.repositories.models.UserDataModel;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CreateUserCaseIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserJpaRepository userJpaRepository;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @Test
  void shouldCreateUser() throws JsonProcessingException, Exception {
    CreateUserUserCaseRequest request = CreateUserUserCaseRequest.builder()
        .name("Jonh Snow")
        .email("jonh.snow@gmail.com")
        .password("123456")
        .role(UserRole.ADMIN)
        .build();

    String mockEncodedPassword = "façfakdfdafd";
    when(passwordEncoder.encode(request.getPassword())).thenReturn(mockEncodedPassword);

    mockMvc.perform(
        post("/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.name").value(request.getName()))
        .andExpect(jsonPath("$.email").value(request.getEmail()))
        .andExpect(jsonPath("$.role").value(request.getRole().toString()));

    UserDataModel userData = userJpaRepository.findByEmail(request.getEmail()).get();

    assertEquals(request.getName(), userData.getName());
    assertEquals(request.getEmail(), userData.getEmail());
    assertEquals(mockEncodedPassword, userData.getPassword());
    assertEquals(request.getRole(), userData.getRole());

  }
}
