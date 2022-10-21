package com.victorsantos.processmanagementapi.users.data;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDataGateway {
  public UserDataResponse save(SaveUserDataRequest request);

  public Optional<UserDataResponse> findByEmail(String email);

  public Page<UserDataResponse> findAll(Pageable pageable);
}
