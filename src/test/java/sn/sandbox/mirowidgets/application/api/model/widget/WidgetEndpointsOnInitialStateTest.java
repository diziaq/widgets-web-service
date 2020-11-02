package sn.sandbox.mirowidgets.application.api.model.widget;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import sn.sandbox.mirowidgets.application.api.WebEndpointsBase;
import sn.sandbox.mirowidgets.testhelp.StringCapture;


@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
class WidgetEndpointsOnInitialStateTest extends WebEndpointsBase {

  private static StringCapture RESOURCE;

  @BeforeAll
  static void setupFakeData() {
    RESOURCE = StringCapture.fromLocalResource("fakedata/WidgetEndpointsOnInitialStateTest");
  }

  @Test
  @DisplayName("when initial state then GET /widgets return OK and empty array")
  void testGetWidgets() {

    client.get()
          .uri("/widgets")
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.OK)
          .expectBody().json("[]");
  }

  @Test
  @DisplayName("when initial state then GET /widgets/{id} return NOT_FOUND and error body")
  void testGetWidgetsId() {

    client.get()
          .uri("/widgets/24678")
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
          .expectBody()
          .jsonPath("$.timestamp").exists()
          .jsonPath("$.status").isEqualTo(404)
          .jsonPath("$.error").isEqualTo("Not Found");
  }

  @Test
  @DisplayName("when initial state then PUT /update/{id} return NOT_FOUND and error body")
  void testPutWidgetsId() {

    client.put()
          .uri("/widgets/463863")
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(RESOURCE.from("valid_request_body.json"))
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
          .expectBody()
          .jsonPath("$.timestamp").exists()
          .jsonPath("$.status").isEqualTo(404)
          .jsonPath("$.error").isEqualTo("Not Found");
  }

  @Test
  @DisplayName("when initial state then PATCH /widgets/{id} return NOT_FOUND and error body")
  void testPatchWidgetsId() {

    client.patch()
          .uri("/widgets/562572828")
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(RESOURCE.from("valid_request_body.json"))
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
          .expectBody()
          .jsonPath("$.timestamp").exists()
          .jsonPath("$.status").isEqualTo(404)
          .jsonPath("$.error").isEqualTo("Not Found");
  }

  @Test
  @DisplayName("when initial state then DELETE /widgets/{id} return NOT_FOUND and error body")
  void testDeleteWidgetsId() {

    client.patch()
          .uri("/widgets/938636725")
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
          .expectBody()
          .jsonPath("$.timestamp").exists()
          .jsonPath("$.status").isEqualTo(404)
          .jsonPath("$.error").isEqualTo("Not Found");
  }
}
