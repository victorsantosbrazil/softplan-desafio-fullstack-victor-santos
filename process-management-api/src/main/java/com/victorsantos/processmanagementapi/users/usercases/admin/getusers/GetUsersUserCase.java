package com.victorsantos.processmanagementapi.users.usercases.admin.getusers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetUsersUserCase {
  public Page<GetUsersUserCaseResponse> run(Pageable pageable);
}
