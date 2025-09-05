package co.com.mrcompany.model.role.gateways;

import co.com.mrcompany.model.role.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleRepository {

    Flux<Role> findAll();

    Mono<Role> findById(Integer id);
}
