package sn.sandbox.mirowidgets.testhelp;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;


public interface StringCapture {

  String from(String path, Charset charset);

  default String from(String path) {
    return from(path, StandardCharsets.UTF_8);
  }

  static StringCapture fromLocalResource(String rootPath) {
    return new StringCaptureFromLocalResource(Path.of(rootPath));
  }
}
