package com.victorsantos.processmanagementapi.users.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.users.utils.token.JwtTokenBuilder;
import com.victorsantos.processmanagementapi.users.utils.token.TokenBuilderRequest;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JwtTokenBuilder.class)
class JwtTokenBuilderTest {
  @Autowired
  private JwtTokenBuilder builder;

  @Test
  void shouldCreateToken() {
    String subject = "1";
    Map<String, String> claims = new HashMap<String, String>();

    TokenBuilderRequest request = TokenBuilderRequest.builder()
        .subject(subject)
        .claims(claims)
        .build();

    String expectedToken = "f3adlkfjkçadsnjvnkavdasvda";

    MockedStatic<Jwts> mockStaticJwts = mockStatic(Jwts.class);
    JwtBuilder mockJwtBuilder  = mock(JwtBuilder.class);

    mockStaticJwts.when(Jwts::builder).thenReturn(mockJwtBuilder);
    when(mockJwtBuilder.setSubject(subject)).thenReturn(mockJwtBuilder);
    when(mockJwtBuilder.setClaims(claims)).thenReturn(mockJwtBuilder);
    when(mockJwtBuilder.compact()).thenReturn(expectedToken);

    String token = builder.build(request);

    verify(mockJwtBuilder).setSubject(subject);
    verify(mockJwtBuilder).setClaims(claims);

    assertEquals(expectedToken, token);
  }

}
