package sn.sandbox.mirowidgets.application.core.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sn.sandbox.mirowidgets.application.core.WidgetSpec;
import sn.sandbox.mirowidgets.application.core.WidgetsFacade;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetsRepository;


class WidgetsFacadeWithFullHarnessBasicIntegrityTest {

  private WidgetsFacade facade;

  @BeforeEach
  void setup() {
    facade = WidgetsFacade.createWithRepository(
        WidgetsRepository.newDefaultInMemory(),
        new WidgetsMappingDefault()
    );
  }

  @Test
  @DisplayName("when create then returns instance with given properties")
  void whenCreateThenReturnsInstanceWithGivenProps() {
    var spec = WidgetSpec.from(12647, 4215, -4375241, 2, 3);

    var result = facade.command().create(spec).block();

    assertAll(
        () -> assertNotNull(result.id()),
        () -> assertNotNull(result.lastModifiedTime()),
        () -> assertEquals(12647, result.position().coordX()),
        () -> assertEquals(4215, result.position().coordY()),
        () -> assertEquals(-4375241, result.position().zIndex()),
        () -> assertEquals(2, result.rectangle().width()),
        () -> assertEquals(3, result.rectangle().height())
    );
  }

  @Test
  @DisplayName("when update then returns instance with patched properties")
  void whenUpdateThenReturnsInstanceWithPatchedProps() {

    var specForCreate = WidgetSpec.from(12647, 4215, -4375241, 2, 3);
    var specForUpdate = WidgetSpec.from(null, 3722, 123, null, 300);

    var created = facade.command().create(specForCreate).block();
    var updated = facade.command().update(created.id(), specForUpdate).block();

    assertAll(
        () -> assertEquals(created.id().asString(), updated.id().asString()),
        () -> assertEquals(12647, updated.position().coordX()),
        () -> assertEquals(3722, updated.position().coordY()),
        () -> assertEquals(123, updated.position().zIndex()),
        () -> assertEquals(2, updated.rectangle().width()),
        () -> assertEquals(300, updated.rectangle().height())
    );
  }

  @Test
  @DisplayName("when replace then returns instance with patched properties")
  void whenReplaceThenReturnsInstanceWithNewProps() {

    var specForCreate = WidgetSpec.from(12647, 4215, -4375241, 2, 3);
    var specForReplace = WidgetSpec.from(6, 7, 8, 9, 5);

    var created = facade.command().create(specForCreate).block();
    var replaced = facade.command().replace(created.id(), specForReplace).block();

    assertAll(
        () -> assertEquals(created.id().asString(), replaced.id().asString()),
        () -> assertEquals(6, replaced.position().coordX()),
        () -> assertEquals(7, replaced.position().coordY()),
        () -> assertEquals(8, replaced.position().zIndex()),
        () -> assertEquals(9, replaced.rectangle().width()),
        () -> assertEquals(5, replaced.rectangle().height())
    );
  }

  @Test
  @DisplayName("when item created then findById returns it")
  void whenCreatedThenFindByIdReturnsIt() {

    var specForCreate = WidgetSpec.from(56, 123, 11, 2, 5);

    var created = facade.command().create(specForCreate).block();
    var found = facade.read().one(created.id()).block();

    assertAll(
        () -> assertEquals(created.id().asString(), found.id().asString()),
        () -> assertEquals(56, found.position().coordX()),
        () -> assertEquals(123, found.position().coordY()),
        () -> assertEquals(11, found.position().zIndex()),
        () -> assertEquals(2, found.rectangle().width()),
        () -> assertEquals(5, found.rectangle().height())
    );
  }

  @Test
  @DisplayName("when item deleted then findById returns empty")
  void whenDeletedThenFindByIdReturnEmpty() {

    var specForCreate = WidgetSpec.from(8937, 41526, -24, 435, 32465);

    var created = facade.command().create(specForCreate).block();
    var deleted = facade.command().delete(created.id()).block();
    var found = facade.read().one(created.id()).blockOptional();

    assertTrue(found.isEmpty());
  }
}
