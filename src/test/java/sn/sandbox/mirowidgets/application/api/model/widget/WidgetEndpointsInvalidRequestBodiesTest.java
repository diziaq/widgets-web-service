package sn.sandbox.mirowidgets.application.api.model.widget;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import sn.sandbox.mirowidgets.application.api.WebEndpointsBase;
import sn.sandbox.mirowidgets.testhelp.StringCapture;


class WidgetEndpointsInvalidRequestBodiesTest extends WebEndpointsBase {

  private static StringCapture RESOURCE;

  @BeforeAll
  static void setupFakeData() {
    RESOURCE = StringCapture.fromLocalResource("fakedata/WidgetEndpointsInvalidRequestBodiesTest");
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
  @DisplayName("when invalid parameters in body then PUT /update/{id} return BAD_REQUEST and error body")
  void testPutWidgetsId(int i) {
    String jsonBody = RESOURCE.from("put_request_invalid_body_" + i + ".json");

    client.put()
          .uri("/widgets/463863")
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(jsonBody)
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
          .expectBody()
          .jsonPath("$.timestamp").exists()
          .jsonPath("$.status").isEqualTo(400)
          .jsonPath("$.message").value(x -> assertThat(x, startsWith("Malformed parameters in request")), String.class);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
  @DisplayName("when initial state then PATCH /widgets/{id} return SER and error body")
  void testPatchWidgetsId(int i) {
    String jsonBody = RESOURCE.from("patch_request_invalid_body_" + i + ".json");

    client.patch()
          .uri("/widgets/6849393627")
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(jsonBody)
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
          .expectBody()
          .jsonPath("$.timestamp").exists()
          .jsonPath("$.status").isEqualTo(400)
          .jsonPath("$.message").value(x -> assertThat(x, startsWith("Malformed parameters in request")), String.class);
  }
}
