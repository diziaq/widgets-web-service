package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class DoubleKeyRackForWidgetPersistentDuplicateKeysHandlingTest {

  private DoubleKeyRackForWidgetPersistent rack;

  @BeforeEach
  void setup() {
    var strategy = DoubleKeyMaintenanceStrategy.just(
        (WidgetPersistent w) -> w.id,
        w -> w.z,
        w -> incrementZ(w)
    );

    rack = new DoubleKeyRackForWidgetPersistent(strategy);
  }

  private static WidgetPersistent incrementZ(WidgetPersistent wp) {
    return new WidgetPersistent(wp.id, wp.x, wp.y, wp.z + 1, wp.width, wp.height, 0);
  }

  @Test
  @DisplayName("when inject instance with duplicated key1 then error")
  void whenInjectDuplicateByKey1ThenError() {
    var wp1 = new WidgetPersistent("twywmmd", 2504, 5373, 74974, 12326, 50, 0);
    var wp2 = new WidgetPersistent("twywmmd", 38652, 333, 0, 18943, 60, 10);
    rack.inject(wp1);

    assertThrows(RuntimeException.class, () -> rack.inject(wp2));
  }

  @Test
  @DisplayName("when inject instance with duplicated key2 then both injected")
  void whenInjectDuplicateByKey2ThenBothInjected() {
    var wp1 = new WidgetPersistent("dktlytt", 2504, 5373, 123, 12326, 2672, 0);
    var wp2 = new WidgetPersistent("yktrqyw", 38652, 333, 123, 18943, 8938, 10);
    rack.inject(wp1);
    rack.inject(wp2);

    var read1 = rack.readByKey1("dktlytt");
    var read2 = rack.readByKey1("yktrqyw");

    assertAll(
        () -> assertEquals("dktlytt", read1.id),
        () -> assertEquals("yktrqyw", read2.id)
    );
  }
}
