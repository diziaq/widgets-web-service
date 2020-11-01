package sn.sandbox.mirowidgets.application.core.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sn.sandbox.mirowidgets.application.core.Id;
import sn.sandbox.mirowidgets.application.core.WidgetSpec;
import sn.sandbox.mirowidgets.application.core.WidgetsFacade;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetsRepository;


class WidgetsFacadeWithFullHarnessNotFoundCasesTest {

  private WidgetsFacade facade;

  @BeforeEach
  void setup() {
    facade = WidgetsFacade.createWithRepository(
        WidgetsRepository.newDefaultInMemory(),
        new WidgetsMappingDefault()
    );
  }

  @Test
  @DisplayName("when initial state then read one returns empty result")
  void whenInitialThenReadOneReturnsEmpty() {
    var result = facade.read().one(Id.fromString("a")).blockOptional();

    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("when initial state then read all returns empty collection")
  void whenInitialThenReadAllReturnsEmpty() {
    var result = facade.read().all().collectList().block();

    assertEquals(0, result.size());
  }

  @Test
  @DisplayName("when initial state then update by id returns empty")
  void whenInitialThenUpdateReturnsEmpty() {
    var id = Id.fromString("a");
    var spec = WidgetSpec.from(844, -4265, 211, 1, 8965);

    var result = facade.command().update(id, spec).blockOptional();

    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("when initial state then replace by id returns empty")
  void whenInitialThenReplaceReturnsEmpty() {
    var id = Id.fromString("a");
    var spec = WidgetSpec.from(5161, 7268, 5674, 2627, 2763);

    var result = facade.command().replace(id, spec).blockOptional();

    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("when initial state then delete by id returns empty")
  void whenInitialThenDeleteReturnsEmpty() {
    var id = Id.fromString("a");

    var result = facade.command().delete(id).blockOptional();

    assertTrue(result.isEmpty());
  }
}
