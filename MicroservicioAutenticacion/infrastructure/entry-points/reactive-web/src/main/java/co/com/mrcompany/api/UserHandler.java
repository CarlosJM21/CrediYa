package co.com.mrcompany.api;

import co.com.mrcompany.api.Dto.Request.UserRequestDto;
import co.com.mrcompany.api.mappers.UserMapper;
import co.com.mrcompany.usecase.user.IUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    private ServerRequest serverRequest;

    @Operation(summary = "Create New User.", description = "Allow Add new User.")
    public Mono<ServerResponse> New(ServerRequest serverRequest) {

        return serverRequest.bodyToMono( UserRequestDto.class)
                .log( "create" )
                .map(mapper::toDomain)
                .flatMap(UserUC::create)
                .map(mapper::toResponse)
                .flatMap(ServerResponse.ok()::bodyValue);

    }

    @Operation(summary = "Retreive User associated with Id.", description = "Fetch User Object associated with the Id provided.")
    public Mono<ServerResponse> User(ServerRequest serverRequest) {

        UUID id = UUID.nameUUIDFromBytes(serverRequest.pathVariable( "Id").getBytes());

        return UserUC.findById(id)
                .map(mapper::toResponse)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    @Operation(summary = "Retreive The List of Users.", description = "Fetch List of users.")
    public Mono<ServerResponse> Users(ServerRequest serverRequest) {
        this.serverRequest = serverRequest;
        return UserUC.findAll()
                .map(mapper::toResponse)
                .collectList()
                .flatMap( u -> ServerResponse.ok().bodyValue(u));
    }

    @Operation(summary = "Update User Information.", description = "Allow Edit user information for especified Id.")
    public  Mono<ServerResponse> Edit (ServerRequest serverRequest){
        return serverRequest.bodyToMono( UserRequestDto.class)
                .map(mapper::toDomain)
                .flatMap(UserUC::edit)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    @Operation(summary = "Delete User Information.", description = "Allow Delete user for especified Id.")
    public Mono<ServerResponse> Delete(ServerRequest serverRequest) {
        UUID id = UUID.nameUUIDFromBytes(serverRequest.pathVariable( "Id").getBytes());
        return UserUC.delete(id).flatMap(ServerResponse.ok()::bodyValue);
    }
}
