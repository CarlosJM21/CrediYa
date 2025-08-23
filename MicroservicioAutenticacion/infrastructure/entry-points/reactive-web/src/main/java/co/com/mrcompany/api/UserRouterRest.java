package co.com.mrcompany.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler handler) {
        return route(POST("/api/Users/New"), handler::New)
                .andRoute(GET("/api/Users/"), handler::Users)
                .and(route(GET("/api/Users/{Id}"), handler::User))
                .and(route().PUT("/api/Users/", handler::Edit).build())
                .and(route().DELETE("/api/Users/{Id}", handler::Delete).build());
    }
}
