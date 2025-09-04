package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.user.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRoleUseCase {
    Flux<Role> findAll() ;

    Mono<Role> findById(Integer id);
}
