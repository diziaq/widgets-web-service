package sn.sandbox.mirowidgets.application.core.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sn.sandbox.mirowidgets.application.core.WidgetSpec;
import sn.sandbox.mirowidgets.application.core.WidgetsFacade;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetsRepository;


class WidgetsFacadeWithFullHarnessMutationsTest {

  private WidgetsFacade facade;

  @BeforeEach
  void setup() {
    facade = WidgetsFacade.createWithRepository(
        WidgetsRepository.newDefaultInMemory(),
        new WidgetsMappingDefault()
    );
  }

  @Test
  @DisplayName("when create with same zIndex then shift previous")
  void whenCreateWithSameZThenShiftPrevious() {
    var specFirst = WidgetSpec.from(127, 4215, 14, 2, 3);
    var specSecond = WidgetSpec.from(235, 436, 14, 9, 7);

    var created1 = facade.command().create(specFirst).block();
    var created2 = facade.command().create(specSecond).block();

    var readFirst = facade.read().one(created1.id()).block();
    var readSecond = facade.read().one(created2.id()).block();

    assertAll(
        () -> assertNotEquals(readFirst.id().asString(), readSecond.id().asString()),
        () -> assertEquals(127, readFirst.position().coordX()),
        () -> assertEquals(235, readSecond.position().coordX()),
        () -> assertEquals(15, readFirst.position().zIndex()),
        () -> assertEquals(14, readSecond.position().zIndex())
    );
  }

  @Test
  @DisplayName("when replace with same zIndex then shift previous")
  void whenReplaceWithSameZThenShiftPrevious() {
    var specFirst = WidgetSpec.from(127, 965, 14, 2, 3);
    var specSecond = WidgetSpec.from(235, -996, 100, 9, 7);
    var specThird = WidgetSpec.from(890, 244, 100, 9, 7);

    var createdFirst = facade.command().create(specFirst).block();
    var createdSecond = facade.command().create(specSecond).block();
    var createdThird = facade.command().replace(createdFirst.id(), specThird).block();

    var readFirst = facade.read().one(createdFirst.id()).block();
    var readSecond = facade.read().one(createdSecond.id()).block();

    assertAll(
        () -> assertEquals(readFirst.id().asString(), createdThird.id().asString()),
        () -> assertNotEquals(readFirst.id().asString(), readSecond.id().asString()),
        () -> assertEquals(890, readFirst.position().coordX()),
        () -> assertEquals(235, readSecond.position().coordX()),
        () -> assertEquals(100, readFirst.position().zIndex()),
        () -> assertEquals(101, readSecond.position().zIndex())
    );
  }
}
