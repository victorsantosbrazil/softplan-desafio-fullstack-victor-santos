package com.victorsantos.processmanagementapi.initializers;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres")
      .withDatabaseName("process_management")
      .withUsername("postgres").withPassword("postgres");

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    postgreSQLContainer.start();

    TestPropertyValues.of(
        "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
        "spring.datasource.username=" + postgreSQLContainer.getUsername(),
        "spring.datasource.password=" + postgreSQLContainer.getPassword())
        .applyTo(applicationContext.getEnvironment());

  }
}
