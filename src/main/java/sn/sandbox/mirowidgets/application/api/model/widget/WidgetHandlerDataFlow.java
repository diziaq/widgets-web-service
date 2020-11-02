package sn.sandbox.mirowidgets.application.api.model.widget;

import java.time.format.DateTimeFormatter;
import sn.sandbox.mirowidgets.application.api.exception.MalformedRequestException;
import sn.sandbox.mirowidgets.application.core.Id;
import sn.sandbox.mirowidgets.application.core.Widget;
import sn.sandbox.mirowidgets.application.core.WidgetSpec;


final class WidgetHandlerDataFlow {

  Id idFrom(String value) {
    return Id.fromString(value);
  }

  WidgetResponseJsonBody toResponse(Widget widget) {
    return WidgetResponseJsonBody
               .init()
               .id(widget.id().asString())
               .lastModifiedTime(widget.lastModifiedTime().asDateTime(DateTimeFormatter.ISO_ZONED_DATE_TIME))
               .coords(widget.position().coordX(), widget.position().coordY())
               .zIndex(widget.position().zIndex())
               .measures(widget.rectangle().width(), widget.rectangle().height())
               .build();
  }

  WidgetSpec specForPostOrPut(WidgetRequestJsonBody r) {

    if (r.id != null) {
      throw new MalformedRequestException("id in body is not required for this action");
    } else if (r.x == null || r.y == null || r.zIndex == null) {
      throw new MalformedRequestException("(x, y, zIndex) are required for this action");
    } else if (r.width == null || r.width <= 0) {
      throw new MalformedRequestException("positive (width) is required for this action");
    } else if (r.height == null || r.height <= 0) {
      throw new MalformedRequestException("positive (height) is required for this action");
    }

    return WidgetSpec.from(r.x, r.y, r.zIndex, r.width, r.height);
  }

  WidgetSpec specForPatch(WidgetRequestJsonBody r) {

    if (r.id != null) {
      throw new MalformedRequestException("id in body is not required for this action");
    } else if (allAreNulls(r.x, r.y, r.zIndex, r.width, r.height)) {
      throw new MalformedRequestException("At least one parameter (x, y, zIndex, width, height) is expected for this action");
    } else if (r.width != null && r.width <= 0) {
      throw new MalformedRequestException("Parameter (width) is expected to be positive");
    } else if (r.height != null && r.height <= 0) {
      throw new MalformedRequestException("Parameter (height) is expected to be positive");
    }

    return WidgetSpec.from(r.x, r.y, r.zIndex, r.width, r.height);
  }

  private boolean allAreNulls(Object... objects) {
    for (Object x : objects) {
      if (x != null) {
        return false;
      }
    }
    return true;
  }
}
