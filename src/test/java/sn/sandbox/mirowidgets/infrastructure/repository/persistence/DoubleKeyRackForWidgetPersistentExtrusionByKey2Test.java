package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


class DoubleKeyRackForWidgetPersistentExtrusionByKey2Test {

  private DoubleKeyRackForWidgetPersistent rack;

  @BeforeEach
  void setup() {
    var strategy = DoubleKeyMaintenanceStrategy.just(
        (WidgetPersistent w) -> w.id,
        w -> w.z,
        w -> incrementZ(w));

    rack = new DoubleKeyRackForWidgetPersistent(strategy);

    populateRackWithDensityOnKey2();
  }

  private void populateRackWithDensityOnKey2() {
    rack.inject(dummyWidget("AAAA", -50));
    rack.inject(dummyWidget("BBBB", -30));
    rack.inject(dummyWidget("CCCC", 0));
    rack.inject(dummyWidget("DDDD", 10));
    rack.inject(dummyWidget("EEEE", 20));
    rack.inject(dummyWidget("FFFF", 30));
    rack.inject(dummyWidget("GGGG", 40));
    rack.inject(dummyWidget("HHHH", 70));
  }

  private static WidgetPersistent incrementZ(WidgetPersistent wp) {
    return new WidgetPersistent(wp.id, wp.x, wp.y, wp.z + 10, wp.width, wp.height, wp.lastModifiedTime);
  }

  private static WidgetPersistent dummyWidget(String key1, Integer key2) {
    return new WidgetPersistent(key1, -1, -2, key2, -3, -4, 0);
  }

  private static String describeArray(WidgetPersistent[] array) {
    return Arrays.stream(array)
                 .sorted(Comparator.comparingInt(a -> a.z))
                 .map(e -> e.z + ":" + e.id)
                 .collect(Collectors.joining(", ", "[", "]"));
  }

  @ParameterizedTest
  @MethodSource("plugSamples")
  @DisplayName("when key X clashes with existing key then only minimal set of items are shifted")
  void whenInjectDuplicateByKey2ShiftOthers(String plugKey1, Integer plugKey2, String expectedDumpDescription) {
    var wp = dummyWidget(plugKey1, plugKey2);

    rack.inject(wp);

    var dump = rack.dumpAll();

    assertEquals(expectedDumpDescription, describeArray(dump));
  }

  static Stream<Arguments> plugSamples() {
    return Stream.of(
        Arguments.of("PLUG", -90, "[-90:PLUG, -50:AAAA, -30:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", -60, "[-60:PLUG, -50:AAAA, -30:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", -50, "[-50:PLUG, -40:AAAA, -30:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", -40, "[-50:AAAA, -40:PLUG, -30:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", -30, "[-50:AAAA, -30:PLUG, -20:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", -20, "[-50:AAAA, -30:BBBB, -20:PLUG, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", -10, "[-50:AAAA, -30:BBBB, -10:PLUG, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", 0_0, "[-50:AAAA, -30:BBBB, 0:PLUG, 10:CCCC, 20:DDDD, 30:EEEE, 40:FFFF, 50:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", 10, "[-50:AAAA, -30:BBBB, 0:CCCC, 10:PLUG, 20:DDDD, 30:EEEE, 40:FFFF, 50:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", 20, "[-50:AAAA, -30:BBBB, 0:CCCC, 10:DDDD, 20:PLUG, 30:EEEE, 40:FFFF, 50:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", 30, "[-50:AAAA, -30:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:PLUG, 40:FFFF, 50:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", 40, "[-50:AAAA, -30:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:PLUG, 50:GGGG, 70:HHHH]"),
        Arguments.of("PLUG", 50, "[-50:AAAA, -30:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 50:PLUG, 70:HHHH]"),
        Arguments.of("PLUG", 60, "[-50:AAAA, -30:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 60:PLUG, 70:HHHH]"),
        Arguments.of("PLUG", 70, "[-50:AAAA, -30:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 70:PLUG, 80:HHHH]"),
        Arguments.of("PLUG", 80, "[-50:AAAA, -30:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 70:HHHH, 80:PLUG]"),
        Arguments.of("PLUG", 90, "[-50:AAAA, -30:BBBB, 0:CCCC, 10:DDDD, 20:EEEE, 30:FFFF, 40:GGGG, 70:HHHH, 90:PLUG]")
    );
  }
}
