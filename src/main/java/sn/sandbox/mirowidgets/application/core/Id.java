package sn.sandbox.mirowidgets.application.core;

import sn.sandbox.mirowidgets.application.core.impl.Mold;


public interface Id {

  String asString();

  static Id fromString(String value){
    return Mold.idFromString(value);
  }
}
