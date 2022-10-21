package com.victorsantos.processmanagementapi.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCaseRequest;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCaseResponse;

@RestController
@RequestMapping("users")
public class UserController {

  @Autowired
  private CreateUserUserCase createUserUserCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateUserUserCaseResponse createUser(@RequestBody CreateUserUserCaseRequest request) {
    return createUserUserCase.run(request);
  }

}
