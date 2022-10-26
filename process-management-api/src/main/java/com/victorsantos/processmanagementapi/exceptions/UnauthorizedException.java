package com.victorsantos.processmanagementapi.exceptions;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String msg) {
    super(msg);
  }

  public UnauthorizedException(String msg, Throwable throwable) {
    super(msg, throwable);
  }

}
