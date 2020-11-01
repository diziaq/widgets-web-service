package sn.sandbox.mirowidgets.infrastructure.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface WidgetsRepository {

  Mono<WidgetEntity> selectById(String id);

  Flux<WidgetEntity> selectAll();

  Mono<WidgetEntity> insert(WidgetEntity entity);

  Mono<WidgetEntity> replaceById(WidgetEntity entity);

  Mono<WidgetEntity> updateById(WidgetEntity entity);

  Mono<Void> deleteById(String id);
}
