package sn.sandbox.mirowidgets.application.core.impl;

import java.util.Objects;
import sn.sandbox.mirowidgets.application.core.*;
import sn.sandbox.mirowidgets.infrastructure.repository.WidgetEntity;


final class WidgetsMappingDefault implements WidgetsMapping {

  @Override
  public Widget makeWidget(WidgetEntity entity) {
    return new WidgetImpl(
        new IdNormal(entity.id()),
        new PositionDefault(entity.x(), entity.y(), entity.z()),
        Mold.rectangleOf(entity.width(), entity.height()),
        Mold.utcTimestampFromMillis(entity.lastModifiedTime())
    );
  }

  @Override
  public WidgetEntity makeEntity(WidgetSpec spec) {
    return new WidgetEntityBare(null, spec);
  }

  @Override
  public WidgetEntity makeEntity(Id id, WidgetSpec spec) {
    return new WidgetEntityBare(id.asString(), spec);
  }

  private static class WidgetImpl implements Widget {

    private final Id id;
    private final Position position;
    private final Rectangle rectangle;
    private final UtcTimestamp lastModifiedTime;

    WidgetImpl(Id id, Position position, Rectangle rectangle, UtcTimestamp lastModifiedTime) {
      this.id = Objects.requireNonNull(id);
      this.position = Objects.requireNonNull(position);
      this.rectangle = Objects.requireNonNull(rectangle);
      this.lastModifiedTime = Objects.requireNonNull(lastModifiedTime);
    }

    @Override
    public Id id() {
      return id;
    }

    @Override
    public Position position() {
      return position;
    }

    @Override
    public Rectangle rectangle() {
      return rectangle;
    }

    @Override
    public UtcTimestamp lastModifiedTime() {
      return lastModifiedTime;
    }
  }


  private static class WidgetEntityBare implements WidgetEntity {

    private final String id;
    private final Integer x;
    private final Integer y;
    private final Integer z;
    private final Integer width;
    private final Integer height;

    WidgetEntityBare(String id, WidgetSpec s) {
      this.id = id;
      this.x = s.x();
      this.y = s.y();
      this.z = s.zIndex();
      this.width = s.width();
      this.height = s.height();
    }

    @Override
    public String id() {
      return id;
    }

    @Override
    public Integer x() {
      return x;
    }

    @Override
    public Integer y() {
      return y;
    }

    @Override
    public Integer z() {
      return z;
    }

    @Override
    public Integer width() {
      return width;
    }

    @Override
    public Integer height() {
      return height;
    }

    @Override
    public Long lastModifiedTime() {
      return null;
    }
  }
}
