package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

public interface DoubleKeyRack<K1, K2, V> {

  V[] dumpAll();

  V readByKey1(K1 key1);

  V readByKey2(K2 key2);

  V inject(V value);

  V ejectByKey1(K1 key1);
}
