package co.com.mrcompany.usecase.user;

import DomainException.EmailExistsException;
import DomainException.WrongSalaryRangeException;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.IPasswordEncoder;
import co.com.mrcompany.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.UUID;

@RequiredArgsConstructor
public class UserUseCase  implements  IUserUseCase{

    private final UserRepository repository;
    private final IPasswordEncoder encoder;

    @Override
    public Mono<User> create(User user) {
        return validRangeSalary(user)
                .flatMap(this::NotExistsUser)
                .map(u -> {
                             u.setPassword(encoder.encodeSimple(u.getPassword()));
                            return u;
                })
                .flatMap(repository::save);
    }

    @Override
    public Flux<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<User> findById(UUID id) {
      return  repository.findById(id);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Mono<Boolean> edit(User user) {

       return UserIsNull(user)
               .flatMap(this::NotExistsUser)
               .flatMap(repository::save)
               .hasElement();
    }

    @Override
    public Mono<Boolean> delete(UUID id) {
        return validUUID(id).flatMap(repository::delete);
    }

    protected Mono<UUID> validUUID(UUID id){
        return Mono.just(id)
                    .filter(x -> id != null && !id.toString().trim().isEmpty() )
                    .switchIfEmpty(Mono.error(new NullPointerException("The values of field \"Id\" don't allow null")));
    }

    protected Mono<User> UserIsNull(User user)
    {
        return Mono.just(user)
                    .filter(u -> user != null)
                    .switchIfEmpty(Mono.error(new NullPointerException("The User to edit can't be null.")));
    }

    protected Mono<User> validRangeSalary(User user)
    {
        return Mono.just(user).filter(u -> u.getBaseSalary().compareTo( BigInteger.ZERO)  >= 0 &&
                                                 u.getBaseSalary().compareTo(BigInteger.valueOf(15000000)) <= 0)
                .switchIfEmpty(Mono.error( new WrongSalaryRangeException()));
    }

    protected Mono<User> NotExistsUser(User user)
    {
        return  repository.existsByEmail(user.getEmail())
                          .filter(u -> !u)
                          .switchIfEmpty(Mono.error(new EmailExistsException()))
                          .then(Mono.just(user));
    }

}
