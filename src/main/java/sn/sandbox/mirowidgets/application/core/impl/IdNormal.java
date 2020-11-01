package sn.sandbox.mirowidgets.application.core.impl;

import java.util.Objects;
import sn.sandbox.mirowidgets.application.core.Id;


final class IdNormal implements Id {

  private final String value;

  IdNormal(String value) {
    this.value = Objects.requireNonNull(value);
  }

  @Override
  public String asString() {
    return value;
  }
}
