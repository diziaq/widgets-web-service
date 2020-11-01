package sn.sandbox.mirowidgets.application.core.impl;

import sn.sandbox.mirowidgets.application.core.*;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetsRepository;


public final class Mold {

  private Mold() {
    // namespace
  }

  static Rectangle rectangleOf(int positiveWidth, int positiveHeight) {
    if (positiveWidth <= 0) {
      throw new RuntimeException("Unexpected value for positive width: " + positiveWidth);
    } else if (positiveHeight <= 0) {
      throw new RuntimeException("Unexpected value for positive height: " + positiveHeight);
    }

    return new RectanglePositive(positiveWidth, positiveHeight);
  }

  static UtcTimestamp utcTimestampFromMillis(Long epochMilliSeconds) {
    if (epochMilliSeconds == null) {
      return UtcTimestampNull.INSTANCE;
    } else {
      return new UtcTimestampStandard(epochMilliSeconds);
    }
  }

  public static WidgetsFacade widgetsFacadeBasedOnRepository(WidgetsRepository repository, WidgetsMapping mapping) {
    return new WidgetsFacadeBasedOnRepository(repository, mapping);
  }

  public static WidgetsMapping widgetsMappingDefault() {
    return new WidgetsMappingDefault();
  }

  public static Id idFromString(String value) {

    if (value == null || value.isBlank()) {
      return IdUnknown.INSTANCE;
    }

    return new IdNormal(value);
  }

  public static WidgetSpec widgetSpecFrom(Integer x, Integer y, Integer zIndex, Integer positiveWidth, Integer positiveHeight) {

    if (positiveWidth != null && positiveWidth <= 0) {
      throw new RuntimeException("Unexpected value for positive width: " + positiveWidth);
    } else if (positiveHeight != null && positiveHeight <= 0) {
      throw new RuntimeException("Unexpected value for positive height: " + positiveHeight);
    }

    return new WidgetSpecImpl(x, y, zIndex, positiveWidth, positiveHeight);
  }
}
