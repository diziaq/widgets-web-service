package sn.sandbox.mirowidgets.application.api.misc;

import java.net.URI;
import java.util.function.Function;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public final class Responses {

  private Responses() {
    // namespace
  }

  public static <V> Mono<ServerResponse> created(Mono<V> responseBody, String pathHead, Function<V, String> getResultId) {
    return responseBody.flatMap(
        m ->
            ServerResponse
                .created(URI.create(pathHead + "/" + getResultId.apply(m)).normalize())
                .bodyValue(m)
    );
  }

  public static Mono<ServerResponse> ok(Mono<?> responseBody) {
    return responseBody.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                       .flatMap(m -> ServerResponse.ok().bodyValue(m));
  }

  public static Mono<ServerResponse> ok(Flux<?> responseBody) {
    return ServerResponse.ok().body(responseBody, Object.class);
  }

  public static Mono<ServerResponse> noContent(Mono<Void> responseBody) {
    return ServerResponse.status(HttpStatus.NO_CONTENT).body(responseBody, Object.class);
  }
}
