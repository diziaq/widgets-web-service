package sn.sandbox.mirowidgets.application.core.impl;

import sn.sandbox.mirowidgets.application.core.Position;


final class PositionDefault implements Position {


  private final int x;
  private final int y;
  private final int z;

  PositionDefault(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public int coordX() {
    return x;
  }

  @Override
  public int coordY() {
    return y;
  }

  @Override
  public int zIndex() {
    return z;
  }
}
