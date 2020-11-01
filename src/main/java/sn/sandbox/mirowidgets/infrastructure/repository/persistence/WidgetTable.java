package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface WidgetTable<K1, K2, V> {

  Flux<V> findAll();

  Mono<V> findById(K1 key1);

  Mono<V> insert(V value);

  Mono<V> delete(K1 key1);

  Mono<V> update(V value);
}
