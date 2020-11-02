package sn.sandbox.mirowidgets.application.core.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sn.sandbox.mirowidgets.application.core.WidgetSpec;
import sn.sandbox.mirowidgets.application.core.WidgetsFacade;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetsRepository;


class WidgetsFacadeWithFullHarnessInvalidSpecTest {

  private WidgetsFacade facade;

  @BeforeEach
  void setup() {
    facade = WidgetsFacade.createWithRepository(
        WidgetsRepository.newDefaultInMemory(),
        new WidgetsMappingDefault()
    );
  }

  @ParameterizedTest
  @MethodSource("invalidSpecsForCreateAndReplace")
  @DisplayName("when call create with invalid spec then error")
  void whenCreateWithInvalidsThenError(WidgetSpec invalidSpec) {
    var created = facade.command().create(invalidSpec);

    assertThrows(RuntimeException.class, () -> created.block());
  }

  @ParameterizedTest
  @MethodSource("invalidSpecsForCreateAndReplace")
  @DisplayName("when call replace with invalid spec then error")
  void whenReplaceWithNullsThenError(WidgetSpec invalidSpec) {

    var specForCreate = WidgetSpec.from(84, 2737, -235, 2, 234);
    var created = facade.command().create(specForCreate).block();

    var replaced = facade.command().replace(created.id(), invalidSpec);

    assertThrows(RuntimeException.class, () -> replaced.block());
  }

  @ParameterizedTest
  @MethodSource("invalidSpecsForUpdate")
  @DisplayName("when call update with invalid spec then error")
  void whenUpdateWithNullsThenError(WidgetSpec invalidSpec) {

    var specForCreate = WidgetSpec.from(84, 2737, -235, 2, 234);
    var created = facade.command().create(specForCreate).block();

    var replaced = facade.command().update(created.id(), invalidSpec);

    assertThrows(RuntimeException.class, () -> replaced.block());
  }

  static Stream<Arguments> invalidSpecsForCreateAndReplace() {
    return Stream.of(
        WidgetSpec.from(null, 24, 124, 4, 234),
        WidgetSpec.from(4151, null, 352, 1, 578),
        WidgetSpec.from(-2626, 73, null, 1, 345),
        WidgetSpec.from(5272, -62, 494, null, 124),
        WidgetSpec.from(3737, 68, 694, 2, null),
        WidgetSpec.from(-4652, -12, 324, -4, 214),
        WidgetSpec.from(3462, 25, 262, 5, -35),
        WidgetSpec.from(1782, 26, 441, 0, 242),
        WidgetSpec.from(-3462, -12, -548, 357, 0)
    ).map(Arguments::of);
  }

  static Stream<Arguments> invalidSpecsForUpdate() {
    return Stream.of(
        WidgetSpec.from(3462, -12, 324, -4, 214),
        WidgetSpec.from(-6573, 25, 262, 5, -35),
        WidgetSpec.from(3462, 26, 441, 0, 242),
        WidgetSpec.from(-7375, -12, -548, 357, 0)
    ).map(Arguments::of);
  }
}
