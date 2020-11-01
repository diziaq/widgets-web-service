package sn.sandbox.mirowidgets.application.core;

import sn.sandbox.mirowidgets.application.core.impl.Mold;


public interface WidgetSpec {

  Integer x();

  Integer y();

  Integer zIndex();

  Integer width();

  Integer height();

  static WidgetSpec from(Integer x, Integer y, Integer zIndex, Integer width, Integer height) {
    return Mold.widgetSpecFrom(x, y, zIndex, width, height);
  }
}
