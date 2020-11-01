package sn.sandbox.mirowidgets.application.core.impl;

import sn.sandbox.mirowidgets.application.core.Id;


final class IdUnknown implements Id {

  static final IdUnknown INSTANCE = new IdUnknown();

  private IdUnknown() {
  }

  @Override
  public String asString() {
    throw new RuntimeException("unknown id");
  }
}
