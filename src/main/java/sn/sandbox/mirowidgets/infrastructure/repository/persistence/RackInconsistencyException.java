package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

final class RackInconsistencyException extends RuntimeException {

  RackInconsistencyException(String s) {
    super("Rack consistency violation. " + s);
  }
}
