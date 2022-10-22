package com.victorsantos.processmanagementapi.users.usercases.admin.deleteuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victorsantos.processmanagementapi.exceptions.NotFoundException;
import com.victorsantos.processmanagementapi.users.data.UserDataGateway;

@Component
public class DeleteUserUserCaseImpl implements DeleteUserUserCase {

  @Autowired
  private UserDataGateway userDataGateway;

  @Override
  public void run(String id) {
    userDataGateway.findById(id).orElseThrow(() -> new NotFoundException("User not found with id " + id));
    userDataGateway.delete(id);

  }

}
