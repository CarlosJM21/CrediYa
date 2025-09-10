package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.UserRepository;
import co.com.mrcompany.r2dbc.entities.UserEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import jakarta.persistence.NamedStoredProcedureQuery;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Transactional
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
        User, UserEntity,UUID, UserR2Repository> implements UserRepository
{
    private final TransactionalOperator tx;
    public UserRepositoryAdapter(UserR2Repository repository, ObjectMapper mapper,
                                 TransactionalOperator transactionalOperator)  {
        super(repository, mapper, d -> mapper.map(d, User.class),transactionalOperator);
        tx = transactionalOperator;
    }

    @Override
    @Query("select u.* from User u  Join  Role r on u.id_rol = r.id where email = :email")
    public Mono<User> findByEmail( @Param("email")String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email)
                         .as(tx::transactional);
    }

    @Override
    public Mono<Boolean> delete(UUID id) {
      return   repository.deleteById(id)
                         .as(tx::transactional)
                         .then(Mono.just(true));
    }
}
