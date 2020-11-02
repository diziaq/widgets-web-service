package sn.sandbox.mirowidgets.application.api.model.widget;

public final class WidgetResponseJsonBody {

  public final String id;
  public final int x;
  public final int y;
  public final int zIndex;
  public final int width;
  public final int height;
  public final String lastModifiedTime;

  private WidgetResponseJsonBody(_Builder b) {
    this.id = b.id;
    this.x = b.x;
    this.y = b.y;
    this.zIndex = b.zIndex;
    this.width = b.width;
    this.height = b.height;
    this.lastModifiedTime = b.lastModifiedTime;
  }

  public static _Builder init() {
    return new _Builder();
  }

  public static class _Builder {

    private String id;
    private int x;
    private int y;
    private int zIndex;
    private int width;
    private int height;
    private String lastModifiedTime;

    private _Builder() {
    }

    public WidgetResponseJsonBody build() {
      return new WidgetResponseJsonBody(this);
    }

    public _Builder id(String id) {
      this.id = id;
      return this;
    }

    public _Builder lastModifiedTime(String lastModifiedTime) {
      this.lastModifiedTime = lastModifiedTime;
      return this;
    }

    public _Builder coords(int x, int y) {
      this.x = x;
      this.y = y;
      return this;
    }

    public _Builder zIndex(int zIndex) {
      this.zIndex = zIndex;
      return this;
    }

    public _Builder measures(int width, int height) {
      this.width = width;
      this.height = height;
      return this;
    }
  }
}


