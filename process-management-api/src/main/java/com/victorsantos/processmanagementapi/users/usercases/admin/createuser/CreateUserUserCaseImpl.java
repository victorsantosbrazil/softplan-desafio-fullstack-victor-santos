package com.victorsantos.processmanagementapi.users.usercases.admin.createuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.victorsantos.processmanagementapi.exceptions.ValidationException;
import com.victorsantos.processmanagementapi.users.data.SaveUserDataRequest;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;
import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.entities.User;
import com.victorsantos.processmanagementapi.users.entities.UserFactory;
import com.victorsantos.processmanagementapi.users.entities.validation.UserValidator;
import com.victorsantos.processmanagementapi.utils.validation.ConstraintViolation;

@Component
public class CreateUserUserCaseImpl implements CreateUserUserCase {

  @Autowired
  private UserDataGateway userDataGateway;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserFactory userFactory;

  @Autowired
  private UserValidator userValidator;

  public CreateUserUserCaseResponse run(CreateUserUserCaseRequest request) {
    User user = userFactory.create(null, request.getName(), request.getEmail(), request.getPassword(),
        request.getRole());

    List<ConstraintViolation> violations = userValidator.validate(user);

    if (violations.size() > 0) {
      throw new ValidationException(violations);
    }

    String encodedPassword = passwordEncoder.encode(request.getPassword());

    SaveUserDataRequest saveDataRequest = SaveUserDataRequest.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(encodedPassword)
        .role(request.getRole())
        .build();

    UserDataResponse saveDataResponse = userDataGateway.save(saveDataRequest);

    return CreateUserUserCaseResponse.builder()
        .id(saveDataResponse.getId())
        .name(request.getName())
        .email(request.getEmail())
        .role(request.getRole())
        .build();
  }
}
