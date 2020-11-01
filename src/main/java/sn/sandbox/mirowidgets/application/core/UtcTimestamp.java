package sn.sandbox.mirowidgets.application.core;

import java.time.format.DateTimeFormatter;


public interface UtcTimestamp {

  Long asMilli();

  String asDateTime(DateTimeFormatter formatter);
}
