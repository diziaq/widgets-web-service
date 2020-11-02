package sn.sandbox.mirowidgets.application.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public final class MalformedRequestException extends ResponseStatusException {

  public MalformedRequestException(String message) {
    super(HttpStatus.BAD_REQUEST, "Malformed parameters in request: " + message);
  }
}
