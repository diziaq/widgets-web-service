package sn.sandbox.mirowidgets.infrastructure.repository;

public interface WidgetEntity {

  String id();

  Integer x();

  Integer y();

  Integer z();

  Integer width();

  Integer height();

  Long lastModifiedTime();
}
