package co.com.mrcompany.api;

import co.com.mrcompany.api.dtos.applicationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
            @RouterOperation( path = "/api/loan/Apply",
            produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, beanClass = Handler.class, beanMethod = "applyToLoan",
            operation = @Operation( operationId = "applyToLoan",
                    responses = { @ApiResponse( responseCode = "200", description = "Successful Operation",
                            content = @Content(schema = @Schema( implementation = applicationRequest.class )))},
                    requestBody = @RequestBody( content = @Content(schema = @Schema(implementation = applicationRequest.class ))))
            )
            }
    )
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/loan/Apply"), handler::applyToLoan);
                //.andRoute(GET("/api/usecase/path"), handler::listenPOSTUseCase)
                //.and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));
    }
}
