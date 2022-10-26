package com.victorsantos.processmanagementapi.users.utils.token;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenBuilder implements TokenBuilder {

  @Override
  public String build(TokenBuilderRequest request) {
    return Jwts.builder()
        .setSubject(request.getSubject())
        .setClaims(request.getClaims())
        .compact();
  }

}
