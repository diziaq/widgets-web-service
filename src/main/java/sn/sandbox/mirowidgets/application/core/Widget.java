package sn.sandbox.mirowidgets.application.core;


public interface Widget {

  Id id();

  Position position();

  Rectangle rectangle();

  UtcTimestamp lastModifiedTime();
}
