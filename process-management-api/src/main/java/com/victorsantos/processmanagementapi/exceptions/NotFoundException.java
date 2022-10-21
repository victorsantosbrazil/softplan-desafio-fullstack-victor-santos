package com.victorsantos.processmanagementapi.exceptions;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String msg) {
    super(msg);
  }

  public NotFoundException(String msg, Throwable throwable) {
    super(msg, throwable);
  }

}
