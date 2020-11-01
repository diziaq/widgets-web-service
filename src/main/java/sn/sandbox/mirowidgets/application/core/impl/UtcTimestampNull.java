package sn.sandbox.mirowidgets.application.core.impl;


import java.time.format.DateTimeFormatter;
import sn.sandbox.mirowidgets.application.core.UtcTimestamp;


final class UtcTimestampNull implements UtcTimestamp {

  static final UtcTimestampNull INSTANCE = new UtcTimestampNull();

  private UtcTimestampNull() {
  }

  @Override
  public Long asMilli() {
    return null;
  }

  @Override
  public String asDateTime(DateTimeFormatter formatter) {
    return "undefined time";
  }

  @Override
  public String toString() {
    return "UtcTimestamp[null]";
  }
}
