package sn.sandbox.mirowidgets.application.api.model.widget;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import sn.sandbox.mirowidgets.application.core.WidgetsFacade;
import sn.sandbox.mirowidgets.application.core.WidgetsMapping;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetsRepository;


@Configuration
public class WidgetRoutes {

  @Bean
  RouterFunction<ServerResponse> widgetsRoutes(WidgetHandler handler) {
    return nest(
        path("widgets"),
        route(GET(""), handler::get)
            .andRoute(GET("{id}"), handler::getById)
            .andRoute(POST(""), handler::post)
    );
  }

  @Bean
  WidgetHandler widgetsHandler() {
    return new WidgetHandler(
        WidgetsFacade.createWithRepository(
            WidgetsRepository.newDefaultInMemory(),
            WidgetsMapping.createDefault()
        ),
        new WidgetHandlerDataFlow()
    );
  }
}
