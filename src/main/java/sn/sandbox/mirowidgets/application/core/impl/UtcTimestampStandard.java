package sn.sandbox.mirowidgets.application.core.impl;


import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import sn.sandbox.mirowidgets.application.core.UtcTimestamp;


final class UtcTimestampStandard implements UtcTimestamp {

  private final long milliseconds;

  UtcTimestampStandard(long milliseconds) {
    this.milliseconds = milliseconds;
  }

  @Override
  public Long asMilli() {
    return milliseconds;
  }

  @Override
  public String asDateTime(DateTimeFormatter formatter) {
    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC).format(formatter);
  }

  @Override
  public String toString() {
    return "UtcTimestamp[" + milliseconds + "]";
  }
}
