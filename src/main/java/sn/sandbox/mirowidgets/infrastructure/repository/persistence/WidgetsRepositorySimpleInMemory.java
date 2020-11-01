package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import java.util.Comparator;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetEntity;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetsRepository;


final class WidgetsRepositorySimpleInMemory implements WidgetsRepository {

  private final PersistencePipe<WidgetEntity, WidgetPersistent, String> conversion;
  private final WidgetTable<String, Integer, WidgetPersistent> storage;

  WidgetsRepositorySimpleInMemory(
      PersistencePipe<WidgetEntity, WidgetPersistent, String> conversion,
      WidgetTable<String, Integer, WidgetPersistent> storage
  ) {
    this.conversion = conversion;
    this.storage = storage;
  }

  @Override
  public Mono<WidgetEntity> selectById(String id) {
    return storage.findById(id)
                  .map(conversion::unpack);
  }

  @Override
  public Flux<WidgetEntity> selectAll() {
    return storage.findAll()
                  .map(conversion::unpack)
                  .sort(Comparator.comparingInt(WidgetEntity::z));
  }

  @Override
  public Mono<WidgetEntity> insert(WidgetEntity entity) {
    var id = UUID.randomUUID().toString();

    return Mono.just(entity)
               .map(e -> conversion.pack(id, e))
               .flatMap(storage::insert)
               .map(conversion::unpack);
  }

  @Override
  public Mono<WidgetEntity> replaceById(WidgetEntity entity) {
    var id = entity.id();

    return Mono.just(entity)
               .map(e -> conversion.pack(id, e))
               .flatMap(storage::update)
               .map(conversion::unpack);
  }

  @Override
  public Mono<WidgetEntity> updateById(WidgetEntity entity) {
    var id = entity.id();

    return Mono.just(id)
               .flatMap(storage::findById)
               .map(conversion::unpack)
               .map(orig -> conversion.merge(orig, entity))
               .flatMap(storage::update)
               .map(conversion::unpack);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return storage.delete(id).then();
  }
}
