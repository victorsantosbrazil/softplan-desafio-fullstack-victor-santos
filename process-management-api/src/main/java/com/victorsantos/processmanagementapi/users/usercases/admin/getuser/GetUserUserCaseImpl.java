package com.victorsantos.processmanagementapi.users.usercases.admin.getuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victorsantos.processmanagementapi.exceptions.NotFoundException;
import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;

@Component
public class GetUserUserCaseImpl implements GetUserUserCase {

  @Autowired
  private UserDataGateway userDataGateway;

  @Override
  public GetUserUserCaseResponse run(String id) {
    return userDataGateway.findById(id).map(this::mapToUserCaseResponse)
        .orElseThrow(() -> new NotFoundException("User not found with id " + id));

  }

  private GetUserUserCaseResponse mapToUserCaseResponse(UserDataResponse userData) {
    return GetUserUserCaseResponse.builder()
        .id(userData.getId())
        .name(userData.getName())
        .email(userData.getEmail())
        .role(userData.getRole())
        .build();
  }

}
