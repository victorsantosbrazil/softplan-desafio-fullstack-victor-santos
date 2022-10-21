package com.victorsantos.processmanagementapi.users.data.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.victorsantos.processmanagementapi.users.data.repositories.models.UserDataModel;

@Repository
public interface UserJpaRepository extends JpaRepository<UserDataModel, UUID> {
  Optional<UserDataModel> findByEmail(String email);
}
