package sn.sandbox.mirowidgets.application.core;

import sn.sandbox.mirowidgets.application.core.impl.Mold;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetEntity;


public interface WidgetsMapping {

  Widget makeWidget(WidgetEntity entity);

  WidgetEntity makeEntity(WidgetSpec spec);

  WidgetEntity makeEntity(Id id, WidgetSpec spec);

  static WidgetsMapping createDefault() {
    return Mold.widgetsMappingDefault();
  }
}
