package sn.sandbox.mirowidgets.application.core.impl;

import sn.sandbox.mirowidgets.application.core.WidgetSpec;


final class WidgetSpecImpl implements WidgetSpec {

  private final Integer x;
  private final Integer y;
  private final Integer zIndex;
  private final Integer width;
  private final Integer height;

  WidgetSpecImpl(Integer x, Integer y, Integer zIndex, Integer width, Integer height) {

    this.x = x;
    this.y = y;
    this.zIndex = zIndex;
    this.width = width;
    this.height = height;
  }

  @Override
  public Integer x() {
    return x;
  }

  @Override
  public Integer y() {
    return y;
  }

  @Override
  public Integer zIndex() {
    return zIndex;
  }

  @Override
  public Integer width() {
    return width;
  }

  @Override
  public Integer height() {
    return height;
  }
}
