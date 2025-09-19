package co.com.mrcompany.api;

import co.com.mrcompany.api.dtos.SetStatusRequest;
import co.com.mrcompany.api.dtos.applicationRequest;
import co.com.mrcompany.model.application.ApplicationDetail;
import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.status.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

import static javax.management.Query.in;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
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
            ),
            @RouterOperation( path = "/api/status",
                    produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = Handler.class, beanMethod = "statusList",
                    operation = @Operation( operationId = "statusList",
                            responses = { @ApiResponse(responseCode = "200", description = "Get All Status.",
                                    content = @Content(schema = @Schema(implementation = Status.class)))}
                    )
            ),
            @RouterOperation( path = "/api/types",
                    produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = Handler.class, beanMethod = "loanTypes",
                    operation = @Operation( operationId = "loanTypes",
                            responses = { @ApiResponse(responseCode = "200", description = "Get All Loan Types.",
                            content = @Content( schema = @Schema(implementation = LoanType.class)))}
                    )
            ),
            @RouterOperation( path = "/api/loan/details",
                    produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = Handler.class, beanMethod = "appDetails",
                    operation = @Operation( operationId = "appDetails",
                            responses = { @ApiResponse(responseCode = "200", description = "Get Loan application paginated.",
                                    content = @Content( schema = @Schema(implementation = ApplicationDetail.class)))},
                            parameters = {
                                    @Parameter(name = "page", description = "Page number", example = "1", required = true),
                                    @Parameter(name = "size", description = "pagination's size ", example = "3", required = true),
                                    @Parameter(name = "status", description = "status of application loan ", example = "pending")
                            }
                    )
            ),
            @RouterOperation( path = "/api/loan/setStatus",
                    produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PATCH, beanClass = Handler.class, beanMethod = "setStatus",
                    operation = @Operation( operationId = "setStatus",
                            responses = { @ApiResponse(responseCode = "200", description = "Set status Loan application.",
                                    content = @Content( schema = @Schema(implementation = Integer.class)))},
                            requestBody = @RequestBody( content = @Content(schema = @Schema(implementation = SetStatusRequest.class )))
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/loan/Apply"), handler::applyToLoan)
                .andRoute(GET("/api/status"), handler::statusList)
                .andRoute(GET("/api/types"), handler::loanTypes)
                .andRoute(GET("/api/loan/details"), handler::appDetails)
                .andRoute(PATCH("/api/loan/setStatus"), handler::setStatus)
                .andRoute(GET("/api/loan/test"), handler::testOk);
    }
}