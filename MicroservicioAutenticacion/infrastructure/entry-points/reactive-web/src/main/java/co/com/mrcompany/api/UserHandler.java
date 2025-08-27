package co.com.mrcompany.api;

import co.com.mrcompany.api.Dto.Request.UserRequestDto;
import co.com.mrcompany.api.mappers.UserMapper;
import co.com.mrcompany.usecase.user.IUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Component
@Transactional
@RequiredArgsConstructor
public class UserHandler {
private  final IUserUseCase UserUC;
private final UserMapper mapper;

    @Operation(summary = "Create New User.", description = "Allow Add new User.")
    public Mono<ServerResponse> New(ServerRequest serverRequest) {

        return serverRequest.bodyToMono( UserRequestDto.class)
                .log( "create" )
                .map(mapper::toDomain)
                .flatMap(UserUC::create)
                .map(mapper::toResponse)
                .flatMap(ServerResponse.ok()::bodyValue);

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retreive User associated with Id.", description = "Fetch User Object associated with the Id provided.")
    public Mono<ServerResponse> User(ServerRequest serverRequest) {
        UUID id = UUID.fromString(serverRequest.pathVariable("Id")); // UUID.nameUUIDFromBytes(serverRequest.pathVariable( "Id").getBytes());

        return UserUC.findById(id)
                .log("findByid")
                .map(mapper::toResponse)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    @Operation(summary = "Retreive The List of Users.", description = "Fetch List of users.")
    public Mono<ServerResponse> Users(ServerRequest serverRequest) {
        return UserUC.findAll()
                .log("ListUsers")
                .map(mapper::toResponse)
                .collectList()
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    @Operation(summary = "Update User Information.", description = "Allow Edit user information for especified Id.")
    public  Mono<ServerResponse> Edit (ServerRequest serverRequest){
        return serverRequest.bodyToMono( UserRequestDto.class)
                .log("Edit")
                .map(mapper::toDomain)
                .flatMap(UserUC::edit)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    @Operation(summary = "Delete User Information.", description = "Allow Delete user for especified Id.")
    public Mono<ServerResponse> Delete(ServerRequest serverRequest) {
        UUID id = UUID.fromString(serverRequest.pathVariable("Id")); //UUID.nameUUIDFromBytes(serverRequest.pathVariable( "Id").getBytes());
        return UserUC.delete(id)
                     .log("Delete")
                     .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> UserByEmail(ServerRequest serverRequest){
        String email= serverRequest.pathVariable( "Email");
        return UserUC.findByEmail (email)
                     .log("FindByEmail")
                     .flatMap(ServerResponse.ok()::bodyValue);
    }
}
