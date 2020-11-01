package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

public final class WidgetPersistent {

  public final String id;
  public final int x;
  public final int y;
  public final int z;
  public final int width;
  public final int height;
  public final long lastModifiedTime;

  public WidgetPersistent(
      String id,
      int x,
      int y,
      int z,
      int width,
      int height,
      long lastModifiedTime
  ) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.z = z;
    this.width = width;
    this.height = height;
    this.lastModifiedTime = lastModifiedTime;
  }
}
