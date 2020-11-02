package sn.sandbox.mirowidgets.application.api.model.widget;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sn.sandbox.mirowidgets.application.api.misc.Responses;
import sn.sandbox.mirowidgets.application.core.WidgetsFacade;


final class WidgetHandler {

  private final WidgetsFacade facade;
  private final WidgetHandlerDataFlow dataFlow;

  WidgetHandler(WidgetsFacade facade, WidgetHandlerDataFlow dataFlow) {
    this.facade = facade;
    this.dataFlow = dataFlow;
  }

  /*
   */
  Mono<ServerResponse> getById(ServerRequest request) {
    var id = request.pathVariable("id");
    var responseBody = Mono.just(id)
                           .map(dataFlow::idFrom)
                           .flatMap(x -> facade.read().one(x))
                           .map(dataFlow::toResponse);

    return Responses.ok(responseBody);
  }

  /*
   */
  Mono<ServerResponse> get(ServerRequest request) {
    var responseBody = facade.read().all().map(dataFlow::toResponse);

    return Responses.ok(responseBody);
  }

  /*
   */
  Mono<ServerResponse> post(ServerRequest request) {
    var responseBody = request.bodyToMono(WidgetRequestJsonBody.class)
                              .map(dataFlow::specForPostOrPut)
                              .flatMap(r -> facade.command().create(r))
                              .map(dataFlow::toResponse);

    return Responses.created(responseBody, "widgets", x -> x.id);
  }
}
