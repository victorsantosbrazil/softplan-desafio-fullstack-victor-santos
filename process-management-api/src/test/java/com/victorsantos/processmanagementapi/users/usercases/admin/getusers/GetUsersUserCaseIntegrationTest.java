package com.victorsantos.processmanagementapi.users.usercases.admin.getusers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
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
class GetUsersUserCaseIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserJpaRepository userJpaRepository;

  @AfterEach
  public void setup() {
    userJpaRepository.deleteAll();
  }

  @Test
  void shouldGetPageOfUserFromAllUsers() throws JsonProcessingException, Exception {
    List<UserDataModel> userDataModels = new ArrayList<>();

    Integer totalElements = 10;
    Integer pageNumber = 1;
    Integer pageSize = 3;
    Integer totalPages = (int) Math.ceil((double) totalElements / pageSize);

    for (int i = 1; i <= totalElements; i++) {
      UserDataModel userModel = UserDataModel.builder()
          .name("User " + i)
          .email("user." + i + "@test.com")
          .password(UUID.randomUUID().toString())
          .role("ADMIN")
          .build();

      userDataModels.add(userModel);
    }

    userJpaRepository.saveAll(userDataModels);

    mockMvc.perform(get("/users")
        .param("size", pageSize.toString())
        .param("page", pageNumber.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").isNotEmpty())
        .andExpect(jsonPath("$.content[0].name").value("User 4"))
        .andExpect(jsonPath("$.content[0].role").value("ADMIN"))
        .andExpect(jsonPath("$.content[1].id").isNotEmpty())
        .andExpect(jsonPath("$.content[1].name").value("User 5"))
        .andExpect(jsonPath("$.content[1].role").value("ADMIN"))
        .andExpect(jsonPath("$.content[2].id").isNotEmpty())
        .andExpect(jsonPath("$.content[2].name").value("User 6"))
        .andExpect(jsonPath("$.content[2].role").value("ADMIN"))
        .andExpect(jsonPath("$.pageable.pageSize").value(pageSize))
        .andExpect(jsonPath("$.pageable.pageNumber").value(pageNumber))
        .andExpect(jsonPath("$.numberOfElements").value(pageSize))
        .andExpect(jsonPath("$.totalPages").value(totalPages))
        .andExpect(jsonPath("$.totalElements").value(totalElements))
        .andExpect(jsonPath("$.first").value(false))
        .andExpect(jsonPath("$.last").value(false));
  }

}
