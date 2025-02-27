package com.victorsantos.processmanagementapi.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCaseRequest;
import com.victorsantos.processmanagementapi.users.usercases.admin.createuser.CreateUserUserCaseResponse;
import com.victorsantos.processmanagementapi.users.usercases.admin.deleteuser.DeleteUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.getuser.GetUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.getuser.GetUserUserCaseResponse;
import com.victorsantos.processmanagementapi.users.usercases.admin.getusers.GetUsersUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.getusers.GetUsersUserCaseResponse;
import com.victorsantos.processmanagementapi.users.usercases.admin.updateuser.UpdateUserUserCase;
import com.victorsantos.processmanagementapi.users.usercases.admin.updateuser.UpdateUserUserCaseRequest;
import com.victorsantos.processmanagementapi.users.usercases.admin.updateuser.UpdateUserUserCaseResponse;

@RestController
@RequestMapping("users")
public class UserController {

  @Autowired
  private CreateUserUserCase createUserUserCase;

  @Autowired
  private GetUsersUserCase getUsersUserCase;

  @Autowired
  private GetUserUserCase getUserUserCase;

  @Autowired
  private UpdateUserUserCase updateUserUserCase;

  @Autowired
  private DeleteUserUserCase deleteUserUserCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateUserUserCaseResponse createUser(@RequestBody CreateUserUserCaseRequest request) {
    return createUserUserCase.run(request);
  }

  @GetMapping
  public Page<GetUsersUserCaseResponse> getUsers(Pageable pageable) {
    return getUsersUserCase.run(pageable);
  }

  @GetMapping("/{id}")
  public GetUserUserCaseResponse getUser(@PathVariable("id") String id) {
    return getUserUserCase.run(id);
  }

  @PatchMapping("/{id}")
  public UpdateUserUserCaseResponse updateUser(
      @PathVariable("id") String id,
      @RequestBody UpdateUserUserCaseRequest request) {
    request.setId(id);
    return updateUserUserCase.run(request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable("id") String id) {
    deleteUserUserCase.run(id);
  }

}
