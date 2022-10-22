package com.victorsantos.processmanagementapi.users.usercases.admin.updateuser;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victorsantos.processmanagementapi.exceptions.NotFoundException;
import com.victorsantos.processmanagementapi.exceptions.ValidationException;
import com.victorsantos.processmanagementapi.users.data.SaveUserDataRequest;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;
import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.entities.User;
import com.victorsantos.processmanagementapi.users.entities.UserFactory;
import com.victorsantos.processmanagementapi.users.entities.validation.UserValidator;
import com.victorsantos.processmanagementapi.utils.validation.ConstraintViolation;

@Component
public class UpdateUserUserCaseImpl implements UpdateUserUserCase {

  @Autowired
  private UserDataGateway userDataGateway;

  @Autowired
  private UserFactory userFactory;

  @Autowired
  private UserValidator userValidator;

  public UpdateUserUserCaseResponse run(UpdateUserUserCaseRequest request) {
    UserDataResponse currentUserData = userDataGateway.findById(request.getId())
        .orElseThrow(() -> new NotFoundException("User not found with id " + request.getId()));

    User user = userFactory.create(request.getId(), request.getName(), request.getEmail(), null,
        request.getRole());

    List<ConstraintViolation> violations = userValidator.validate(user, Arrays.asList("password"));

    if (violations.size() > 0) {
      throw new ValidationException("Validation error", violations);
    }

    SaveUserDataRequest saveDataRequest = SaveUserDataRequest.builder()
        .id(request.getId())
        .name(request.getName())
        .email(request.getEmail())
        .password(currentUserData.getPassword())
        .role(request.getRole())
        .build();

    UserDataResponse saveDataResponse = userDataGateway.save(saveDataRequest);

    return UpdateUserUserCaseResponse.builder()
        .id(saveDataResponse.getId())
        .name(request.getName())
        .email(request.getEmail())
        .role(request.getRole())
        .build();
  }
}
