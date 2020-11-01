package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;


public interface DoubleKeyMaintenanceStrategy<K1, K2, V> {

  K1 key1Of(V value);

  K2 key2Of(V value);

  V shiftKey2(V value);

  static <K1, K2, V> DoubleKeyMaintenanceStrategy<K1, K2, V> just(
      Function<V, K1> extractKey1,
      Function<V, K2> extractKey2,
      UnaryOperator<V> shiftKey2
  ) {
    Objects.requireNonNull(extractKey1);
    Objects.requireNonNull(extractKey2);
    Objects.requireNonNull(shiftKey2);

    return new DoubleKeyMaintenanceStrategy<>() {
      @Override
      public K1 key1Of(V value) {
        return extractKey1.apply(value);
      }

      @Override
      public K2 key2Of(V value) {
        return extractKey2.apply(value);
      }

      @Override
      public V shiftKey2(V value) {
        return shiftKey2.apply(value);
      }
    };
  }
}
