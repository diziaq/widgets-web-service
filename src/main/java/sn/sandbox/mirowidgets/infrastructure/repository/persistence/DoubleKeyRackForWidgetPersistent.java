package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import java.util.HashMap;
import java.util.Map;


final class DoubleKeyRackForWidgetPersistent implements DoubleKeyRack<String, Integer, WidgetPersistent> {

  private final Map<String, WidgetPersistent> accessByKey1 = new HashMap<>();
  private final Map<Integer, WidgetPersistent> accessByKey2 = new HashMap<>();
  private final DoubleKeyMaintenanceStrategy<String, Integer, WidgetPersistent> strategy;

  DoubleKeyRackForWidgetPersistent(
      DoubleKeyMaintenanceStrategy<String, Integer, WidgetPersistent> strategy
  ) {
    this.strategy = strategy;
  }

  @Override
  public WidgetPersistent[] dumpAll() {
    return accessByKey1.values().toArray(new WidgetPersistent[0]);
  }

  @Override
  public WidgetPersistent readByKey1(String key1) {
    return accessByKey1.get(key1);
  }

  @Override
  public WidgetPersistent readByKey2(Integer key2) {
    return accessByKey2.get(key2);
  }

  @Override
  public WidgetPersistent inject(WidgetPersistent w) {
    var key1 = strategy.key1Of(w);
    var key2 = strategy.key2Of(w);

    if (accessByKey1.containsKey(key1)) {
      throw new RackInconsistencyException("Attempt to inject duplicate key " + key1);
    } else if (accessByKey2.containsKey(key2)) {
      shuffleFrom(w);
    } else {
      injectExtrusive(w);
    }

    return w;
  }

  @Override
  public WidgetPersistent ejectByKey1(String key1) {
    WidgetPersistent w = accessByKey1.remove(key1);

    if (w != null) {
      var key2 = strategy.key2Of(w);
      accessByKey2.remove(key2);
    }

    return w;
  }

  private void shuffleFrom(WidgetPersistent widget) {
    var currentWidget = widget;

    while (true) {
      currentWidget = injectExtrusive(currentWidget);

      if (currentWidget == null) {
        break;
      }

      currentWidget = shiftKey2Safe(currentWidget);
    }
  }

  private WidgetPersistent injectExtrusive(WidgetPersistent newWidget) {
    var key1 = strategy.key1Of(newWidget);
    var key2 = strategy.key2Of(newWidget);

    accessByKey1.put(key1, newWidget);

    return accessByKey2.put(key2, newWidget);
  }

  private WidgetPersistent shiftKey2Safe(WidgetPersistent initial) {
    int initialKey = strategy.key2Of(initial);
    var shifted = strategy.shiftKey2(initial);
    int shiftedKey = strategy.key2Of(shifted);

    if (initialKey == shiftedKey) {
      throw new RackInconsistencyException("Key shifting strategy returned unchanged key " + initialKey);
    }

    return shifted;
  }
}
