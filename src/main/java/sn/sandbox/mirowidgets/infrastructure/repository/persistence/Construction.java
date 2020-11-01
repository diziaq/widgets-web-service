package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import sn.sandbox.mirowidgets.infrastructure.repository.WidgetsRepository;


public final class Construction {

  private Construction() {
    // namespace
  }

  public static WidgetsRepository defaultWidgetsInMemoryRepository() {

    var strategy = new DoubleKeyStrategyDefaultForWidgetPersistent();
    var rack = new DoubleKeyRackForWidgetPersistent(strategy);

    return new WidgetsRepositorySimpleInMemory(
        new PersistencePipeForWidgets(),
        new WidgetTableAcid<>(rack, strategy)
    );
  }
}
