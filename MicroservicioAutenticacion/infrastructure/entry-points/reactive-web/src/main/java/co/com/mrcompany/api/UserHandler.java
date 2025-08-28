package co.com.mrcompany.api;

import co.com.mrcompany.api.Dto.Request.UserRequestDto;
import co.com.mrcompany.api.Exception.GlobalExceptionHandler;
import co.com.mrcompany.api.mappers.UserMapper;
import co.com.mrcompany.usecase.user.IUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class UserHandler {

private final IUserUseCase userUC;
private final UserMapper mapper;

private static String badrequest = "Ocurrio un Error interno";

    @Operation(summary = "Create New User.", description = "Allow Add new User.")
    public Mono<ServerResponse> userNew(ServerRequest serverRequest) {
        URI location = URI.create("/api/Users/New");

        return serverRequest.bodyToMono( UserRequestDto.class)
                .log( "create" )
                .map(mapper::toDomain)
                .flatMap(userUC::create)
                .map(mapper::toResponse)
                .flatMap(ServerResponse.created(location)::bodyValue);
                //.doOnError(e -> log.error("exception:{}",e) );
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retreive User associated with Id.", description = "Fetch User Object associated with the Id provided.")
    public Mono<ServerResponse> user(ServerRequest serverRequest) {
        UUID id = UUID.fromString(serverRequest.pathVariable("Id"));

        return userUC.findById(id)
                .log("findByid")
                .map(mapper::toResponse)
                .flatMap(ServerResponse.ok()::bodyValue);
                //.switchIfEmpty(Mono.error());
    }

    @Operation(summary = "Retreive The List of Users.", description = "Fetch List of users.")
    public Mono<ServerResponse> users(ServerRequest serverRequest) {
        return userUC.findAll()
                .log("ListUsers")
                .map(mapper::toResponse)
                .collectList()
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    @Operation(summary = "Update User Information.", description = "Allow Edit user information for especified Id.")
    public  Mono<ServerResponse> edit (ServerRequest serverRequest){
        return serverRequest.bodyToMono( UserRequestDto.class)
                .log("Edit")
                .map(mapper::toDomain)
                .flatMap(userUC::edit)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    @Operation(summary = "Delete User Information.", description = "Allow Delete user for especified Id.")
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        UUID id = UUID.fromString(serverRequest.pathVariable("Id"));
        return userUC.delete(id)
                     .log("Delete")
                     .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> userByEmail(ServerRequest serverRequest){
        String email= serverRequest.pathVariable( "Email");
        return userUC.findByEmail (email)
                     .log("FindByEmail")
                     .map(mapper::toResponse)
                     //.switchIfEmpty(Mono.error(new NotFoundException("User not found")))
                     .flatMap(ServerResponse.ok()::bodyValue);
              //  .doOnError(ServerResponse.badRequest()::bodyValue(badrequest));
    }
}
