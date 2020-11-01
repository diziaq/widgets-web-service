package sn.sandbox.mirowidgets.application.core.impl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.sandbox.mirowidgets.application.core.Id;
import sn.sandbox.mirowidgets.application.core.Widget;
import sn.sandbox.mirowidgets.application.core.WidgetSpec;
import sn.sandbox.mirowidgets.application.core.WidgetsFacade;
import sn.sandbox.mirowidgets.application.core.WidgetsMapping;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetsRepository;


public final class WidgetsFacadeBasedOnRepository implements WidgetsFacade {

  private final WidgetsRepository repository;
  private final WidgetsMapping mapping;

  public WidgetsFacadeBasedOnRepository(
      WidgetsRepository repository,
      WidgetsMapping mapping
  ) {
    this.repository = repository;
    this.mapping = mapping;
  }

  @Override
  public Query read() {
    return new QueryImpl();
  }

  @Override
  public Command command() {
    return new CommandImpl();
  }

  private class QueryImpl implements Query {

    @Override
    public Mono<Widget> one(Id id) {
      return repository.selectById(id.asString())
                       .map(mapping::makeWidget);
    }

    @Override
    public Flux<Widget> all() {
      return repository.selectAll()
                       .map(mapping::makeWidget);
    }
  }


  public final class CommandImpl implements Command {

    @Override
    public Mono<Widget> create(WidgetSpec spec) {
      return repository.insert(mapping.makeEntity(spec))
                       .map(mapping::makeWidget);
    }

    @Override
    public Mono<Widget> update(Id id, WidgetSpec spec) {
      return repository.updateById(mapping.makeEntity(id, spec))
                       .map(mapping::makeWidget);
    }

    @Override
    public Mono<Widget> replace(Id id, WidgetSpec spec) {
      return repository.replaceById(mapping.makeEntity(id, spec))
                       .map(mapping::makeWidget);
    }

    @Override
    public Mono<Void> delete(Id id) {
      return repository.deleteById(id.asString());
    }
  }
}
