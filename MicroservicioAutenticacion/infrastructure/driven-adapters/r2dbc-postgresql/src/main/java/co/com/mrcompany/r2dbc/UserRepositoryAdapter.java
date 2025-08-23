package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.UserRepository;
import co.com.mrcompany.r2dbc.Entities.UserEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.jmx.export.notification.ModelMBeanNotificationPublisher;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
    User, UserEntity,UUID, UserR2Repository> implements UserRepository
{
    public UserRepositoryAdapter(UserR2Repository repository, ObjectMapper mapper)  {
         super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Mono<Boolean> delete(UUID id) {
         repository.deleteById(id);
         return Mono.just(true);
    }
}
