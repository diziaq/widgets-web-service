package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

public class DoubleKeyStrategyDefaultForWidgetPersistent
    implements DoubleKeyMaintenanceStrategy<String, Integer, WidgetPersistent> {

  @Override
  public String key1Of(WidgetPersistent value) {
    return value.id;
  }

  @Override
  public Integer key2Of(WidgetPersistent value) {
    return value.z;
  }

  @Override
  public WidgetPersistent shiftKey2(WidgetPersistent wp) {
    return new WidgetPersistent(wp.id, wp.x, wp.y, wp.z + 1, wp.width, wp.height, System.currentTimeMillis());
  }
}
