package sn.sandbox.mirowidgets.application.core;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.sandbox.mirowidgets.application.core.impl.Mold;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetsRepository;


public interface WidgetsFacade {

  Query read();

  Command command();

  interface Query {

    Mono<Widget> one(Id id);

    Flux<Widget> all();
  }


  interface Command {

    Mono<Widget> create(WidgetSpec spec);

    Mono<Widget> update(Id id, WidgetSpec spec);

    Mono<Widget> replace(Id id, WidgetSpec spec);

    Mono<Void> delete(Id id);
  }

  static WidgetsFacade createWithRepository(WidgetsRepository repository, WidgetsMapping mapping) {
    return Mold.widgetsFacadeBasedOnRepository(repository, mapping);
  }
}
