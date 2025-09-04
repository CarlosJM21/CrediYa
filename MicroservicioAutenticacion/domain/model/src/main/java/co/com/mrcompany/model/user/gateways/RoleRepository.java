package co.com.mrcompany.model.user.gateways;

import co.com.mrcompany.model.user.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RoleRepository {

    Flux<Role> findAll();

    Mono<Role> findById(Integer id);
}
