package com.victorsantos.processmanagementapi.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorsantos.processmanagementapi.users.usercases.common.login.LoginUserCase;
import com.victorsantos.processmanagementapi.users.usercases.common.login.LoginUserCaseRequest;
import com.victorsantos.processmanagementapi.users.usercases.common.login.LoginUserCaseResponse;

@RestController
@RequestMapping("auth")
public class AuthController {

  @Autowired
  private LoginUserCase loginUserCase;

  @PostMapping("/basic")
  public LoginUserCaseResponse login(@RequestBody LoginUserCaseRequest request) {
    return loginUserCase.run(request);
  }

}
