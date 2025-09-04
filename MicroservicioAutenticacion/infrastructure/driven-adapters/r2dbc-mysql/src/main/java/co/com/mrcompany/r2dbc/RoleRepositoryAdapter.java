package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.user.Role;
import co.com.mrcompany.model.user.gateways.RoleRepository;
import co.com.mrcompany.r2dbc.entities.RoleEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Transactional
public class RoleRepositoryAdapter extends ReactiveAdapterOperations<
        Role, RoleEntity, Integer, RoleR2Repository> implements RoleRepository
{
    private final TransactionalOperator tx;
    public RoleRepositoryAdapter(RoleR2Repository repository, ObjectMapper mapper,
                                 TransactionalOperator transactionalOperator)  {
        super(repository, mapper, d -> mapper.map(d, Role.class),transactionalOperator);
        tx = transactionalOperator;
    }
}
