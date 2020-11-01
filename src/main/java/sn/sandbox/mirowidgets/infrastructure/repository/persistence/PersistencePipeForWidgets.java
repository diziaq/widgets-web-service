package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import java.util.Objects;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetEntity;


final class PersistencePipeForWidgets implements PersistencePipe<WidgetEntity, WidgetPersistent, String> {

  @Override
  public WidgetPersistent pack(String id, WidgetEntity raw) {
    return new WidgetPersistent(
        Objects.requireNonNull(id, "widget id"),
        Objects.requireNonNull(raw.x(), "widget x"),
        Objects.requireNonNull(raw.y(), "widget y"),
        Objects.requireNonNull(raw.z(), "widget z"),
        Objects.requireNonNull(raw.width(), "widget width"),
        Objects.requireNonNull(raw.height(), "widget height"),
        System.currentTimeMillis()
    );
  }

  @Override
  public WidgetPersistent merge(WidgetEntity initial, WidgetEntity patch) {
    return new WidgetPersistent(
        Objects.requireNonNull(initial.id(), "widget id"),
        nvl(patch.x(), initial.x(), "widget x"),
        nvl(patch.y(), initial.y(), "widget y"),
        nvl(patch.z(), initial.z(), "widget z"),
        nvl(patch.width(), initial.width(), "widget width"),
        nvl(patch.height(), initial.height(), "widget height"),
        System.currentTimeMillis()
    );
  }

  @Override
  public WidgetEntity unpack(WidgetPersistent packed) {
    return new WidgetEntitySimple(packed);
  }

  private <V> V nvl(V a, V b, String errorMessage) {
    var value = a == null ? b : a;
    return Objects.requireNonNull(value, errorMessage);
  }

  private static class WidgetEntitySimple implements WidgetEntity {

    private final WidgetPersistent p;

    WidgetEntitySimple(WidgetPersistent persistent) {
      this.p = persistent;
    }

    @Override
    public String id() {
      return p.id;
    }

    @Override
    public Integer x() {
      return p.x;
    }

    @Override
    public Integer y() {
      return p.y;
    }

    @Override
    public Integer z() {
      return p.z;
    }

    @Override
    public Integer width() {
      return p.width;
    }

    @Override
    public Integer height() {
      return p.height;
    }

    @Override
    public Long lastModifiedTime() {
      return p.lastModifiedTime;
    }
  }
}
