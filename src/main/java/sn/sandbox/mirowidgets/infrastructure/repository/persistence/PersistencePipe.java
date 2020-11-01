package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

public interface PersistencePipe<A, B, ID> {

  B pack(ID id, A raw);

  B merge(A initial, A patch);

  A unpack(B packed);
}
