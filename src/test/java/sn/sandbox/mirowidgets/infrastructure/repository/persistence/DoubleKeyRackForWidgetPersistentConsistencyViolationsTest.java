package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class DoubleKeyRackForWidgetPersistentConsistencyViolationsTest {

  @Test
  @DisplayName("when key shifting strategy produces unchanged key then inject duplicate on key2 produces error")
  void whenWrongStrategyThenErrorOnInject() {
    var rack = new DoubleKeyRackForWidgetPersistent(
        DoubleKeyMaintenanceStrategy.just(
            w -> w.id,
            w -> w.z,
            w -> w
        )
    );
    var wp1 = new WidgetPersistent("jslfdjak", 68273, 11245, 123, 3453, 1, 0);
    var wp2 = new WidgetPersistent("sjsgsjss", 36737, 45321, 123, 2443, 2, 10);
    rack.inject(wp1);

    assertThrows(RuntimeException.class, () -> rack.inject(wp2));
  }

  @Test
  @DisplayName("when getter of key1 produces duplicate key then inject produces error")
  void whenDuplicateKey1ThenErrorOnInject() {
    var rack = new DoubleKeyRackForWidgetPersistent(
        DoubleKeyMaintenanceStrategy.just(w -> "abc", w -> w.z, w -> w)
    );
    var wp1 = new WidgetPersistent("anything-1", 1, 3, 5, 7, 1, 0);
    var wp2 = new WidgetPersistent("anything-2", 2, 4, 6, 8, 2, 10);
    rack.inject(wp1);

    assertThrows(RuntimeException.class, () -> rack.inject(wp2));
  }
}
