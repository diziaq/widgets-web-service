package sn.sandbox.mirowidgets.testhelp;


import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Scanner;


final class StringCaptureFromLocalResource implements StringCapture {

  private final Path rootPath;

  StringCaptureFromLocalResource(Path rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String from(String path, Charset charset) {

    var fullPathToResource = rootPath.resolve(path).normalize().toString();
    var inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fullPathToResource);

    try (Scanner scanner = new Scanner(inputStream, charset.name())) {
      return scanner.useDelimiter("\\A").next();
    } catch (Exception e) {
      throw new RuntimeException("Failed to get contents of resource: local path " + fullPathToResource, e);
    }
  }
}
