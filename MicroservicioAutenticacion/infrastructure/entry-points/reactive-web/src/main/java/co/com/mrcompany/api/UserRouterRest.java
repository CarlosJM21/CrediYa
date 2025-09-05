package co.com.mrcompany.api;

import co.com.mrcompany.api.dto.request.LoginDto;
import co.com.mrcompany.api.dto.request.TokenDto;
import co.com.mrcompany.api.dto.request.UserRequestDto;
import co.com.mrcompany.api.dto.response.TokenResponse;
import co.com.mrcompany.api.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
public class UserRouterRest {

    @Bean
    @RouterOperations({
        @RouterOperation( path = "/api/Users",
            produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "users",
            operation = @Operation( operationId = "Users",
            responses = { @ApiResponse(responseCode = "200", description = "Get All Users Successfully.",
                            content = @Content(schema = @Schema(implementation = UserRequestDto.class))) })
        ),
        @RouterOperation( path = "/api/Users/{Id}",
            produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "user",
            operation = @Operation( operationId = "User",
            responses = { @ApiResponse(responseCode = "200", description = "Get User Successfully.",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
                                            @ApiResponse(responseCode = "404", description = "User Not Found for given id.") },
                            parameters = { @Parameter(in = ParameterIn.PATH, name = "Id") })
        ),
        @RouterOperation( path = "/api/Users/New",
                produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, beanClass = UserHandler.class, beanMethod = "userNew",
                operation = @Operation( operationId = "New",
                responses = { @ApiResponse( responseCode = "200", description = "Successful Operation",
                                content = @Content(schema = @Schema( implementation = UserResponseDto.class )))},
                requestBody = @RequestBody( content = @Content(schema = @Schema(implementation = UserRequestDto.class ))))
        ),
        @RouterOperation( path = "/api/Users",
                produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT, beanClass = UserHandler.class, beanMethod = "edit",
                operation = @Operation( operationId = "Edit",
                responses = { @ApiResponse( responseCode = "200", description = "Successful Operation",
                        content = @Content(schema = @Schema( implementation = UserResponseDto.class )))},
                requestBody = @RequestBody( content = @Content(schema = @Schema(implementation = UserRequestDto.class ))))
        ),
        @RouterOperation( path = "/api/Users/{Id}",
                produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE, beanClass = UserHandler.class, beanMethod = "delete",
                operation = @Operation( operationId = "Delete",
                responses = { @ApiResponse( responseCode = "200", description = "Successful Operation",
                        content = @Content(schema =  @Schema( implementation = UserResponseDto.class )))},
                parameters = { @Parameter(in = ParameterIn.PATH, name = "Id") })
        ),
        @RouterOperation( path = "/api/Users/ByEmail/{Email}",
                produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "userByEMail",
                operation = @Operation( operationId = "UserByEmail",
                        responses = { @ApiResponse( responseCode = "200", description = "Successful Operation",
                                content = @Content(schema =  @Schema( implementation = UserResponseDto.class )))},
                        parameters = { @Parameter(in = ParameterIn.PATH, name = "Email") })
        ),
        @RouterOperation( path = "/api/Auth/login",
                produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, beanClass = UserHandler.class, beanMethod = "login",
                operation = @Operation( operationId = "login",
                        responses = { @ApiResponse( responseCode = "200", description = "Successful Operation",
                                content = @Content(schema =  @Schema( implementation = TokenResponse.class )))},
                        requestBody = @RequestBody( content = @Content(schema = @Schema(implementation = LoginDto.class ))))
        ),
        @RouterOperation( path = "/api/Auth/validate",
                produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, beanClass = UserHandler.class, beanMethod = "login",
                operation = @Operation( operationId = "login",
                        responses = { @ApiResponse( responseCode = "200", description = "Successful Operation",
                                content = @Content(schema =  @Schema( implementation = Boolean.class )))},
                        requestBody = @RequestBody( content = @Content(schema = @Schema(implementation = TokenDto.class ))))
        )
    })
    public RouterFunction<ServerResponse> routerFunction(UserHandler handler /*, AuthHandler authHandler*/) {
        return route(POST("/api/Users/New"), handler::userNew)
                .and(route(GET("/api/Users"), handler::users))
                .and(route(GET("/api/Users/{Id}"), handler::user))
                .and(route(GET("/api/Users/ByEmail/{Email}"), handler::userByEmail))
                .and(route().PUT("/api/Users", handler::edit).build())
                .and(route().DELETE("/api/Users/{Id}", handler::delete).build())
                //Auth
                .and(route(POST("/api/Auth/login"), handler::login))
                .and(route(POST("/api/Auth/validate"), handler::validate));
                //.filter(f ->GlobalExceptionHandler); //revisar filtros
    }
}
