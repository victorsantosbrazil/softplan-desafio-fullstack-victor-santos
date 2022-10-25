package com.victorsantos.processmanagementapi.common.responses;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private LocalDateTime timestamp;
  private Integer status;
  private ErrorType error;
  private String message;
  private String path;
}
