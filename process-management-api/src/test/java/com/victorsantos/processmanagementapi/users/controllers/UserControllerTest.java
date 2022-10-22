package com.victorsantos.processmanagementapi.users.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCaseRequest;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCaseResponse;
import com.victorsantos.processmanagementapi.users.usercases.admin.getuser.GetUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.getuser.GetUserUserCaseResponse;
import com.victorsantos.processmanagementapi.users.usercases.admin.getusers.GetUsersUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.getusers.GetUsersUserCaseResponse;
import com.victorsantos.processmanagementapi.users.usercases.admin.updateuser.UpdateUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.updateuser.UpdateUserUserCaseRequest;
import com.victorsantos.processmanagementapi.users.usercases.admin.updateuser.UpdateUserUserCaseResponse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserController.class)
class UserControllerTest {
  @Autowired
  private UserController userController;

  @MockBean
  private CreateUserUserCase createUserUserCase;

  @MockBean
  private GetUsersUserCase getUsersUserCase;

  @MockBean
  private GetUserUserCase getUserUserCase;

  @MockBean
  private UpdateUserUserCase updateUserUserCase;

  @Test
  void createUser() {
    CreateUserUserCaseRequest request = CreateUserUserCaseRequest.builder()
        .name("Jonh Snow")
        .email("jonh.snow@gmail.com")
        .password("123456")
        .role("ADMIN")
        .build();

    CreateUserUserCaseResponse expectedResponse = CreateUserUserCaseResponse.builder()
        .id("fadfalçfda")
        .name(request.getName())
        .email(request.getEmail())
        .role(request.getRole())
        .build();

    when(createUserUserCase.run(request)).thenReturn(expectedResponse);

    CreateUserUserCaseResponse response = userController.createUser(request);

    assertEquals(expectedResponse, response);

  }

  @Test
  void testGetUsers() {
    Pageable pageable = PageRequest.of(0, 20);

    List<GetUsersUserCaseResponse> mockResponseModels = new ArrayList<>();

    GetUsersUserCaseResponse mockUserData1 = GetUsersUserCaseResponse.builder()
        .id("flkdafa")
        .name("John")
        .role("ADMIN")
        .build();

    GetUsersUserCaseResponse mockUserData2 = GetUsersUserCaseResponse.builder()
        .id("flkdafa")
        .name("Victor")
        .role("PROCESS_SCREENER")
        .build();

    GetUsersUserCaseResponse mockUserData3 = GetUsersUserCaseResponse.builder()
        .id("flkdafa")
        .name("Fernando")
        .role("PROCESS_FINISHER")
        .build();

    mockResponseModels.add(mockUserData1);
    mockResponseModels.add(mockUserData2);
    mockResponseModels.add(mockUserData3);

    Page<GetUsersUserCaseResponse> mockResponseModelsPage = new PageImpl<>(mockResponseModels);

    when(getUsersUserCase.run(pageable)).thenReturn(mockResponseModelsPage);

    Page<GetUsersUserCaseResponse> responsePage = userController.getUsers(pageable);

    assertEquals(mockResponseModelsPage, responsePage);

  }

  @Test
  void testGetUser() {

    String id = "fdaçkjfdçkaf";

    GetUserUserCaseResponse mockResponse = GetUserUserCaseResponse.builder()
        .id("flkdafa")
        .name("John")
        .role("ADMIN")
        .build();

    when(getUserUserCase.run(id)).thenReturn(mockResponse);

    GetUserUserCaseResponse response = userController.getUser(id);

    assertEquals(mockResponse, response);

  }

  @Test
  void testUpdateUser() {
    String id = "fçdakfdafsa";
    UpdateUserUserCaseRequest incomingRequest = UpdateUserUserCaseRequest.builder()
        .name("Jonh Snow")
        .email("jonh.snow@gmail.com")
        .role("ADMIN")
        .build();

    UpdateUserUserCaseResponse expectedResponse = UpdateUserUserCaseResponse.builder()
        .id(id)
        .name(incomingRequest.getName())
        .email(incomingRequest.getEmail())
        .role(incomingRequest.getRole())
        .build();

    ArgumentCaptor<UpdateUserUserCaseRequest> userCaseRequestCaptor = ArgumentCaptor
        .forClass(UpdateUserUserCaseRequest.class);

    when(updateUserUserCase.run(userCaseRequestCaptor.capture())).thenReturn(expectedResponse);

    UpdateUserUserCaseResponse response = userController.updateUser(id, incomingRequest);

    assertEquals(id, userCaseRequestCaptor.getValue().getId());
    assertEquals(expectedResponse, response);

  }
}
