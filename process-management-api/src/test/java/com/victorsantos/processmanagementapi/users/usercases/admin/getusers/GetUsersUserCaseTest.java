package com.victorsantos.processmanagementapi.users.usercases.admin.getusers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GetUsersUserCaseImpl.class)
class GetUsersUserCaseTest {

  @Autowired
  private GetUserUserCase userCase;

  @MockBean
  private UserDataGateway userDataGateway;

  @Test
  void shouldGetAllUsers() {
    Pageable pageable = PageRequest.of(0, 10);

    List<UserDataResponse> mockUsersData = new ArrayList<>();

    UserDataResponse mockUserData1 = UserDataResponse.builder()
        .id("flkdafa")
        .name("John")
        .email("john@test.com")
        .role("ADMIN")
        .build();

    UserDataResponse mockUserData2 = UserDataResponse.builder()
        .id("flkdafa")
        .name("Victor")
        .email("victor@test.com")
        .role("PROCESS_SCREENER")
        .build();

    UserDataResponse mockUserData3 = UserDataResponse.builder()
        .id("flkdafa")
        .name("Fernando")
        .email("fernando@test.com")
        .role("PROCESS_FINISHER")
        .build();

    mockUsersData.add(mockUserData1);
    mockUsersData.add(mockUserData2);
    mockUsersData.add(mockUserData3);

    Page<UserDataResponse> mockUserDataPage = new PageImpl<>(mockUsersData);

    when(userDataGateway.findAll(pageable)).thenReturn(mockUserDataPage);

    Page<GetUsersUserCaseResponse> response = userCase.run(pageable);
    Page<GetUsersUserCaseResponse> expectedResponse = mockUserDataPage
        .map((userData) -> new GetUsersUserCaseResponse(userData.getId(), userData.getName(), userData.getRole()));

    assertEquals(expectedResponse, response);

  }
}
