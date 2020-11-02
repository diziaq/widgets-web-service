package sn.sandbox.mirowidgets.application.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import sn.sandbox.mirowidgets.testhelp.StringCapture;


@ContextConfiguration
@WebFluxTest
public abstract class WebEndpointsBase {

  @Autowired
  protected WebTestClient client;

  protected final StringCapture stringCapture(String path) {
    return StringCapture.fromLocalResource(path);
  }

  @Configuration
  @ComponentScan({"sn.sandbox.mirowidgets"})
  public static class SpringConfig {

  }
}
