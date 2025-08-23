package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class UserUseCase  implements  IUserUseCase{

    private final UserRepository repository;

    @Override
    public Mono<User> create(User user) {
        return repository.save(user);
    }

    @Override
    public Flux<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<User> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Boolean> edit(User user) {

        User currentUser = repository.findById(user.getId()).block( );

        if(currentUser== null){
            return Mono.just(false);
        }
        repository.save(user);

        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> delete(UUID id) {
        return repository.delete(id);
    }
}
