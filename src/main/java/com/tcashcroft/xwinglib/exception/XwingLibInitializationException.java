package com.tcashcroft.xwinglib.exception;

/**
 * Exceptions for the xwing-lib.
 */
public class XwingLibInitializationException extends Exception {
  public XwingLibInitializationException(String message) {
    super(message);
  }

  public XwingLibInitializationException(String message, Throwable e) {
    super(message, e);
  }
}
