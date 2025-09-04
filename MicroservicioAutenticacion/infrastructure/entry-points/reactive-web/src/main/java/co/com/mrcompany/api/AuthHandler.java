package co.com.mrcompany.api;

import co.com.mrcompany.api.dto.request.LoginDto;
import co.com.mrcompany.api.dto.request.TokenDto;
import co.com.mrcompany.api.dto.request.UserRequestDto;
import co.com.mrcompany.api.mappers.TokenMapper;
import co.com.mrcompany.api.mappers.UserMapper;
import co.com.mrcompany.usecase.user.ITokenUseCase;
import co.com.mrcompany.usecase.user.IUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class AuthHandler {

    private final ITokenUseCase tokenUC;
    private final UserMapper userMapper;
    private final TokenMapper mapper;

    private static String badrequest = "Ocurrio un Error interno";

    @Operation(summary = "Login.", description = "Allow user's authentication.")
    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        URI location = URI.create("/api/Auth/login");

        return serverRequest.bodyToMono( LoginDto.class)
                .log( "login" )
                .map(userMapper::loginToDomain)
                .flatMap(tokenUC::login)
                .map(mapper::toResponse)
                .flatMap(ServerResponse.created(location)::bodyValue);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retreive User associated with Id.", description = "Fetch User Object associated with the Id provided.")
    public Mono<ServerResponse> validate(ServerRequest serverRequest) {
        URI location = URI.create("/api/Auth/refresh");

        return serverRequest.bodyToMono( TokenDto.class)
                .log( "validate" )
                .map(mapper::toDomain)
                .flatMap(tokenUC::validateToken)
                .map(mapper::toResponse)
                .flatMap(ServerResponse.created(location)::bodyValue);
        //.switchIfEmpty(Mono.error());
    }
}
