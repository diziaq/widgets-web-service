package sn.sandbox.mirowidgets.application.core.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


class MoldCornerCasesTest {

  @Test
  void timestampFromNull() {
    var utcTimestamp = Mold.utcTimestampFromMillis(null);

    assertAll(
        () -> assertEquals("UtcTimestamp[null]", utcTimestamp.toString()),
        () -> assertEquals(null, utcTimestamp.asMilli()),
        () -> assertEquals("undefined time", utcTimestamp.asDateTime(DateTimeFormatter.ISO_DATE))
    );
  }

  @Test
  void timestampFromLegalLong() {
    var utcTimestamp = Mold.utcTimestampFromMillis(1604335750000L);

    assertAll(
        () -> assertEquals("UtcTimestamp[1604335750000]", utcTimestamp.toString()),
        () -> assertEquals(1604335750000L, utcTimestamp.asMilli()),
        () -> assertEquals("2020-11-02T16:49:10Z", utcTimestamp.asDateTime(DateTimeFormatter.ISO_ZONED_DATE_TIME))
    );
  }

  @ParameterizedTest
  @MethodSource("invalidIdValues")
  void idFromUnrecognized(String value) {
    var id = Mold.idFromString(value);

    assertThrows(RuntimeException.class, () -> id.asString());
  }

  @ParameterizedTest
  @MethodSource("invalidMeasures")
  void testInvalidRectanleMeasures(int width, int height) {
    assertThrows(RuntimeException.class, () -> Mold.rectangleOf(width, height));
  }

  @ParameterizedTest
  @MethodSource("invalidMeasures")
  void testInvalidWidgetSpecMeasures(int width, int height) {
    assertThrows(RuntimeException.class, () -> Mold.widgetSpecFrom(1, 2, 3, width, height));
  }

  static Stream<Arguments> invalidMeasures() {
    return Stream.of(
        Arguments.of(12414, -123566),
        Arguments.of(-214121, 214526),
        Arguments.of(-1, 4619824),
        Arguments.of(0, 72575372),
        Arguments.of(0, 0),
        Arguments.of(0, -1123)
    );
  }

  static Stream<Arguments> invalidIdValues() {
    return Stream.of(null, "", " ", "\n").map(Arguments::of);
  }
}
