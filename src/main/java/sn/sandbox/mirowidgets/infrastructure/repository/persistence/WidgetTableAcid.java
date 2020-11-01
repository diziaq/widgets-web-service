package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import java.util.function.Supplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;


final class WidgetTableAcid<K1, K2, V> implements WidgetTable<K1, K2, V> {

  private final DoubleKeyRack<K1, K2, V> rack;
  private final DoubleKeyMaintenanceStrategy<K1, K2, V> strategy;
  private final Scheduler mutationSchedulerSingleThread = Schedulers.newSingle("WidgetTableMutation");

  WidgetTableAcid(DoubleKeyRack<K1, K2, V> rack, DoubleKeyMaintenanceStrategy<K1, K2, V> strategy) {
    this.rack = rack;
    this.strategy = strategy;
  }

  public Flux<V> findAll() {
    return Flux.fromArray(rack.dumpAll());
  }

  public Mono<V> findById(K1 key1) {
    return Mono.justOrEmpty(rack.readByKey1(key1));
  }

  public Mono<V> insert(V value) {
    return asyncAtomic(() -> rack.inject(value));
  }

  public Mono<V> delete(K1 key1) {
    return asyncAtomic(() -> rack.ejectByKey1(key1));
  }

  public Mono<V> update(V value) {
    return asyncAtomic(() -> {
      var key1 = strategy.key1Of(value);
      var ejected = rack.ejectByKey1(key1);

      if (ejected != null) {
        return rack.inject(value);
      } else {
        return null;
      }
    });
  }

  private <U> Mono<U> asyncAtomic(Supplier<U> supplier) {
    return Mono.defer(() -> Mono.fromSupplier(supplier).publishOn(mutationSchedulerSingleThread));
  }
}
