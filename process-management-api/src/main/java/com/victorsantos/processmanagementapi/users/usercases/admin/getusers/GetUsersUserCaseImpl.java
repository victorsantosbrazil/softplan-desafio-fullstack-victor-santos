package com.victorsantos.processmanagementapi.users.usercases.admin.getusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;

@Component
public class GetUsersUserCaseImpl implements GetUserUserCase {
  @Autowired
  private UserDataGateway userDataGateway;

  @Override
  public Page<GetUsersUserCaseResponse> run(Pageable pageable) {
    return userDataGateway.findAll(pageable)
        .map(this::userDataToResponse);
  }

  private GetUsersUserCaseResponse userDataToResponse(UserDataResponse userData) {
    return new GetUsersUserCaseResponse(userData.getId(), userData.getName(), userData.getRole());
  }

}
