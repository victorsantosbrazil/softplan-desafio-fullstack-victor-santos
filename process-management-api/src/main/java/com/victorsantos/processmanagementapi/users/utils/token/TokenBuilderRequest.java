package com.victorsantos.processmanagementapi.users.utils.token;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenBuilderRequest {
  private String subject;
  private Map<String, ?> claims;
}
