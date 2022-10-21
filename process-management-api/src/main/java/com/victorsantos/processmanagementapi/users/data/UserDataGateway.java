package com.victorsantos.processmanagementapi.users.data;

import java.util.Optional;

public interface UserDataGateway {
  public UserDataResponse save(SaveUserDataRequest request);

  public Optional<UserDataResponse> findByEmail(String email);
}
