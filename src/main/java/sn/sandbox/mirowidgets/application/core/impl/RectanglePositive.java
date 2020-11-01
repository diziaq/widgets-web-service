package sn.sandbox.mirowidgets.application.core.impl;

import sn.sandbox.mirowidgets.application.core.Rectangle;


final class RectanglePositive implements Rectangle {

  private final int width;
  private final int height;

  RectanglePositive(int positiveWidth, int positiveHeight) {
    this.width = positiveWidth;
    this.height = positiveHeight;
  }

  @Override
  public int width() {
    return width;
  }

  @Override
  public int height() {
    return height;
  }
}
