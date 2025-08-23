package co.com.mrcompany.api;

import co.com.mrcompany.api.Dto.Request.UserRequestDto;
import co.com.mrcompany.api.mappers.UserMapper;
import co.com.mrcompany.usecase.user.IUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserHandler {
private  final IUserUseCase UserUC;
private final UserMapper mapper;
    private ServerRequest serverRequest;

    public Mono<ServerResponse> New(ServerRequest serverRequest) {

        return serverRequest.bodyToMono( UserRequestDto.class)
                .map(mapper::toDomain)
                .flatMap(UserUC::create)
                .map(mapper::toResponse)
                .flatMap(ServerResponse.ok()::bodyValue);

    }

    public Mono<ServerResponse> User(ServerRequest serverRequest) {

        UUID id = UUID.nameUUIDFromBytes(serverRequest.pathVariable( "Id").getBytes());

        return UserUC.findById(id)
                .map(mapper::toResponse)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> Users(ServerRequest serverRequest) {
        this.serverRequest = serverRequest;
        return UserUC.findAll()
                .map(mapper::toResponse)
                .collectList()
                .flatMap( u -> ServerResponse.ok().bodyValue(u));
    }

    public  Mono<ServerResponse> Edit (ServerRequest serverRequest){
        return serverRequest.bodyToMono( UserRequestDto.class)
                .map(mapper::toDomain)
                .flatMap(UserUC::edit)
                .flatMap(ServerResponse.ok()::bodyValue);
    }


    public Mono<ServerResponse> Delete(ServerRequest serverRequest) {
        UUID id = UUID.nameUUIDFromBytes(serverRequest.pathVariable( "Id").getBytes());
        return UserUC.delete(id).flatMap(ServerResponse.ok()::bodyValue);
    }
}
