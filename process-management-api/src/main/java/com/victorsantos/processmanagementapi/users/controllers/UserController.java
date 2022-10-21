package com.victorsantos.processmanagementapi.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCaseRequest;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCaseResponse;
import com.victorsantos.processmanagementapi.users.usercases.admin.getusers.GetUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.getusers.GetUsersUserCaseResponse;

@RestController
@RequestMapping("users")
public class UserController {

  @Autowired
  private CreateUserUserCase createUserUserCase;

  @Autowired
  private GetUserUserCase getUserUserCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateUserUserCaseResponse createUser(@RequestBody CreateUserUserCaseRequest request) {
    return createUserUserCase.run(request);
  }

  @GetMapping
  public Page<GetUsersUserCaseResponse> getUsers(Pageable pageable) {
    return getUserUserCase.run(pageable);
  }

}
