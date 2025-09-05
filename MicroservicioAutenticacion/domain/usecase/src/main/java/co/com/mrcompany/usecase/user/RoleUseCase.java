package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.role.Role;
import co.com.mrcompany.model.role.gateways.RoleRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RoleUseCase implements IRoleUseCase{

    private final RoleRepository repository;

    @Override
    public Flux<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Role> findById(Integer id) {
        return repository.findById(id);
    }
}
