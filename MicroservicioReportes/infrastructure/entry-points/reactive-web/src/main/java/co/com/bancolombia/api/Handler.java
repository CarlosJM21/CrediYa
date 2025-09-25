package co.com.bancolombia.api;

import co.com.bancolombia.usecase.IReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class Handler {
private  final IReportUseCase useCase;

    public Mono<ServerResponse> Report(ServerRequest serverRequest) {
        return useCase.getReport()
                      .log("getReport")
                      .flatMap(ServerResponse.ok()::bodyValue);
    }
}
