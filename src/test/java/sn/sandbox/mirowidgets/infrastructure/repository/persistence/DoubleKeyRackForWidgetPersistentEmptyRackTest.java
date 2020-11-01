package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class DoubleKeyRackForWidgetPersistentEmptyRackTest {

  private DoubleKeyRackForWidgetPersistent rack;

  @BeforeEach
  void setup() {
    var strategy = DoubleKeyMaintenanceStrategy.just(
        (WidgetPersistent w) -> w.id,
        w -> w.z,
        w -> w
    );

    rack = new DoubleKeyRackForWidgetPersistent(strategy);
  }

  @Test
  @DisplayName("when dumpAll then return empty array")
  void whenDumpThenEmpty() {
    var result = rack.dumpAll();

    assertEquals(0, result.length);
  }

  @Test
  @DisplayName("when readByKey1 then return null")
  void whenReadByKey1ThenNull() {
    var result = rack.readByKey1("adgag");

    assertNull(result);
  }

  @Test
  @DisplayName("when readByKey2 then return null")
  void whenReadByKey2ThenNull() {
    var result = rack.readByKey2(356357);

    assertNull(result);
  }

  @Test
  @DisplayName("when inject instance then return this instance")
  void whenInjectThenReturnOfferedInstance() {
    var wp = new WidgetPersistent("saffsaf", -275, 2723, 10754, 10483, 25267, 0);
    var result = rack.inject(wp);

    assertSame(result, wp);
  }

  @Test
  @DisplayName("when inject instance then readByKey1 return this instance")
  void whenInjectThenReadByKey1ReturnInjecteddInstance() {
    var wp = new WidgetPersistent("fshjlffds", 4745, -45633, 5584, 748368, 94382, 3683);
    rack.inject(wp);
    var result = rack.readByKey1("fshjlffds");

    assertSame(result, wp);
  }

  @Test
  @DisplayName("when inject instance then readByKey2 return this instance")
  void whenInjectThenReadByKey2ReturnInjectedInstance() {
    var wp = new WidgetPersistent("hkdistya", 9338, -145, -74974, 893282, 33828, 368628);
    rack.inject(wp);
    var result = rack.readByKey2(-74974);

    assertSame(wp, result);
  }

  @Test
  @DisplayName("when inject same instance twice then error")
  void whenInjectSameInstanceTwiceThenError() {
    var wp1 = new WidgetPersistent("hkdistya", 4262, 386, 74974, 5, 50, 0);
    rack.inject(wp1);

    assertThrows(RuntimeException.class, () -> rack.inject(wp1));
  }

  @Test
  @DisplayName("when inject and eject by same key1 then rack remains empty")
  void whenInjectAndEjectByKey1SameInstanceTwiceThenError() {
    var wp = new WidgetPersistent("skluww", 572, 25, 537, 2575, 2, 0);
    rack.inject(wp);
    rack.ejectByKey1("skluww");

    var result = rack.dumpAll();

    assertEquals(0, result.length);
  }

  @Test
  @DisplayName("when inject then eject by same key1 then same instance is returned")
  void whenInjectThenEjectByKey1ReturnsSameInstance() {
    var wp = new WidgetPersistent("dkskajj", 794, 436, 73, 15, 261, 0);
    rack.inject(wp);

    var result = rack.ejectByKey1("dkskajj");

    assertSame(wp, result);
  }

  @Test
  @DisplayName("when eject by unused key1 then null is returned")
  void whenEjectByUnusedKey1ThenReturnsNull() {
    var result = rack.ejectByKey1("slkhjsl");

    assertNull(result);
  }
}
