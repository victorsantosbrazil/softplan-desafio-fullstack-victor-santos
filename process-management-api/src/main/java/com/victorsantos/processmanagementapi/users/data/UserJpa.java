package com.victorsantos.processmanagementapi.users.data;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.victorsantos.processmanagementapi.users.data.repositories.UserJpaRepository;
import com.victorsantos.processmanagementapi.users.data.repositories.models.UserDataModel;

@Component
public class UserJpa implements UserDataGateway {

  @Autowired
  private UserJpaRepository userJpaRepository;

  @Override
  public UserDataResponse save(SaveUserDataRequest request) {
    UUID id = request.getId() != null ? UUID.fromString(request.getId()) : null;

    UserDataModel userDataModel = UserDataModel.builder()
        .id(id)
        .name(request.getName())
        .email(request.getEmail())
        .password(request.getPassword())
        .role(request.getRole())
        .build();

    userDataModel = userJpaRepository.save(userDataModel);

    return mapToUserDataResponse(userDataModel);
  }

  @Override
  public Optional<UserDataResponse> findByEmail(String email) {
    return userJpaRepository.findByEmail(email).map(this::mapToUserDataResponse);
  }

  @Override
  public Page<UserDataResponse> findAll(Pageable pageable) {
    return userJpaRepository.findAll(pageable).map(this::mapToUserDataResponse);
  }

  private UserDataResponse mapToUserDataResponse(UserDataModel userDataModel) {
    return UserDataResponse.builder()
        .id(userDataModel.getId().toString())
        .name(userDataModel.getName())
        .email(userDataModel.getEmail())
        .password(userDataModel.getPassword())
        .role(userDataModel.getRole())
        .build();
  }

  @Override
  public Optional<UserDataResponse> findById(String id) {
    return userJpaRepository.findById(UUID.fromString(id)).map(this::mapToUserDataResponse);
  }

  @Override
  public void delete(String id) {
    userJpaRepository.deleteById(UUID.fromString(id));

  }

}
