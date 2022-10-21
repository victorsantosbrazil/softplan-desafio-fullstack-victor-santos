package com.victorsantos.processmanagementapi.users.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.users.data.repositories.UserJpaRepository;
import com.victorsantos.processmanagementapi.users.data.repositories.models.UserDataModel;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserJpa.class)
class UserJpaTest {
  @Autowired
  private UserJpa userJpa;

  @MockBean
  private UserJpaRepository userJpaRepository;

  @Test
  void shouldSaveUserData() {
    SaveUserDataRequest request = SaveUserDataRequest.builder()
        .name("Jonh Snow")
        .email("jonh.snow@gmail.com")
        .password("123456")
        .role("ADMIN")
        .build();

    UUID mockId = UUID.randomUUID();

    UserDataModel mockRepositorySaveResponse = UserDataModel.builder()
        .id(mockId)
        .name(request.getName())
        .email(request.getEmail())
        .password(request.getPassword())
        .role(request.getRole())
        .build();

    ArgumentCaptor<UserDataModel> savedUserDataCaptor = ArgumentCaptor.forClass(UserDataModel.class);

    when(userJpaRepository.save(savedUserDataCaptor.capture())).thenReturn(mockRepositorySaveResponse);

    UserDataResponse response = userJpa.save(request);

    UserDataResponse expectedResponse = UserDataResponse.builder()
        .id(mockId.toString())
        .name(request.getName())
        .email(request.getEmail())
        .password(request.getPassword())
        .role(request.getRole())
        .build();

    assertEquals(expectedResponse, response);
  }

  @Test
  void shouldGetUserDataByEmail() {
    String email = "test@test.com";

    UUID mockId = UUID.randomUUID();

    UserDataModel mockUserDataModel = UserDataModel.builder()
        .id(mockId)
        .name("John Snow")
        .email(email)
        .password("faflfaeklfafda")
        .role("ADMIN")
        .build();

    when(userJpaRepository.findByEmail(email)).thenReturn(Optional.of(mockUserDataModel));

    Optional<UserDataResponse> response = userJpa.findByEmail(email);

    UserDataResponse expectedUserDataResponse = UserDataResponse.builder()
        .id(mockUserDataModel.getId().toString())
        .name(mockUserDataModel.getName())
        .email(mockUserDataModel.getEmail())
        .password(mockUserDataModel.getPassword())
        .role(mockUserDataModel.getRole())
        .build();

    assertEquals(expectedUserDataResponse, response.get());

  }
}
