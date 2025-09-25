package co.com.bancolombia.api;

import co.com.bancolombia.model.Report;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
            @RouterOperation( path = "/api/report",
                    produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = Handler.class, beanMethod = "Report",
                    operation = @Operation( operationId = "Report",
                            responses = { @ApiResponse(responseCode = "200", description = "Get Report.",
                                    content = @Content(schema = @Schema(implementation = Report.class)))}
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/report"), handler::Report);
    }
}
