package sn.sandbox.mirowidgets;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;


@SpringBootTest
class ApplicationCanStartTest {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  @DisplayName("when app is started then application has id")
  void contextContainsAppId() {
    assertEquals("application", applicationContext.getId());
  }

  @Test
  @DisplayName("when app is started then ApplicationMain bean exists in context")
  void contextContainsMainBean() {
    assertNotNull(applicationContext.getBean(ApplicationMain.class));
  }


  @Test
  @DisplayName("when app is started with defaults then environment has active profiles [basic,logging]")
  void environmentContainsSpecificProfiles() {
    var environment = applicationContext.getBean(Environment.class);
    var activeProfiles = List.of(environment.getActiveProfiles());

    assertThat(activeProfiles, contains("basic", "logging"));
  }
}
