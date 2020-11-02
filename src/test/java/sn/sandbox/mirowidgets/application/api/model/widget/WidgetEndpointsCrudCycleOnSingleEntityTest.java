package sn.sandbox.mirowidgets.application.api.model.widget;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import sn.sandbox.mirowidgets.application.api.WebEndpointsBase;
import sn.sandbox.mirowidgets.testhelp.StringCapture;


@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class WidgetEndpointsCrudCycleOnSingleEntityTest extends WebEndpointsBase {

  private static StringCapture RESOURCE;

  @BeforeAll
  static void setupFakeData() {
    RESOURCE = StringCapture.fromLocalResource("fakedata/WidgetEndpointsCrudCycleOnSingleEntityTest");
  }

  @Test
  @DisplayName("ensure GET returns widget created by POST")
  void ensureCanGetCreatedWidget() {

    var idRef = new AtomicReference<String>();

    client.post()
          .uri("/widgets")
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(RESOURCE.from("post_request_body.json"))
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.CREATED)
          .expectBody()
          .jsonPath("$.id").value(x -> {
                                             idRef.set(x);
                                             assertThat(x, matchesPattern("^[-0-9a-f]{36}$"));
                                           }, String.class)
          .jsonPath("$.x").isEqualTo(72372)
          .jsonPath("$.y").isEqualTo(35737)
          .jsonPath("$.zIndex").isEqualTo(-1545)
          .jsonPath("$.width").isEqualTo(345)
          .jsonPath("$.height").isEqualTo(123);

    client.get()
          .uri("/widgets/" + idRef.get())
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.OK)
          .expectBody()
          .jsonPath("$.id").value(x -> assertEquals(x, idRef.get()), String.class)
          .jsonPath("$.x").isEqualTo(72372)
          .jsonPath("$.y").isEqualTo(35737)
          .jsonPath("$.zIndex").isEqualTo(-1545)
          .jsonPath("$.width").isEqualTo(345)
          .jsonPath("$.height").isEqualTo(123);
  }

  @Test
  @DisplayName("ensure GET by id returns nothing after widget is deleted by DELETE")
  void ensureAfterDeleteWidgetIsNotAvailable() {

    var idRef = new AtomicReference<String>();

    client.post()
          .uri("/widgets")
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(RESOURCE.from("post_request_body.json"))
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.CREATED)
          .expectBody()
          .jsonPath("$.id").value(x -> {
                                             idRef.set(x);
                                             assertThat(x, matchesPattern("^[-0-9a-f]{36}$"));
                                           }, String.class)
          .jsonPath("$.x").isEqualTo(72372)
          .jsonPath("$.y").isEqualTo(35737)
          .jsonPath("$.zIndex").isEqualTo(-1545)
          .jsonPath("$.width").isEqualTo(345)
          .jsonPath("$.height").isEqualTo(123);

    client.delete()
          .uri("/widgets/" + idRef.get())
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.NO_CONTENT)
          .expectBody().isEmpty();

    client.get()
          .uri("/widgets/" + idRef.get())
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
          .expectBody()
          .jsonPath("$.timestamp").exists()
          .jsonPath("$.status").isEqualTo(404)
          .jsonPath("$.error").isEqualTo("Not Found");
  }
}
