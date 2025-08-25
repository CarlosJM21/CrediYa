package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.UserRepository;
import co.com.mrcompany.r2dbc.Entities.UserEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Transactional
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
    User, UserEntity,UUID, UserR2Repository> implements UserRepository
{
    public UserRepositoryAdapter(UserR2Repository repository, ObjectMapper mapper)  {
         super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findAll().collectList()
                .flatMap(l-> Mono.just(mapper.map(l.stream().anyMatch(u -> u.getEmail().equals(email)), User.class)));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.findAll().collectList().map(l -> l.stream().anyMatch(u -> u.getEmail().equals(email))) ;
    }

    @Override
    public Mono<Boolean> delete(UUID id) {
         repository.deleteById(id);
         return Mono.just(true);
    }
}
