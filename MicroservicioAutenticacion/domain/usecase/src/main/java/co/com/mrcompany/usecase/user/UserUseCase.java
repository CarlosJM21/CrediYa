package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.UUID;

@RequiredArgsConstructor
public class UserUseCase  implements  IUserUseCase{

    private final UserRepository repository;

    @Override
    public Mono<User> create(User user) {
        return Mono.zip( repository.existsByEmail(user.getEmail()),
                         validRangeSalary(user.getBaseSalary()) )
                .flatMap(h -> {
                        if(h.getT1()){
                            return Mono.error(new IllegalArgumentException("Email already exists"));
                        }

                        if(!h.getT2()){
                            return Mono.error(new IllegalArgumentException("The values of field \"BaseSalary\" must be between 0 and 1500000"));
                        }
                        return repository.save(user);
                    });
    }

    @Override
    public Flux<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<User> findById(UUID id) {
      return  validUUID(id).flatMap( i ->{ return repository.findById(i);});
    }

    @Override
    public Mono<Boolean> edit(User user) {

       return UserIsNull(user)
               .flatMap(u ->repository.findById(u.getId()))
               .flatMap(repository::save)
               .map(u -> { return  u == null; });
    }

    @Override
    public Mono<Boolean> delete(UUID id) {
        return validUUID(id).flatMap(repository::delete);
    }

    private boolean anyPropertyIsNull(User user){
        return  Arrays.stream(user.getClass().getFields()).anyMatch(f -> f == null);
    }

    private Mono<UUID> validUUID(UUID id){
        if(id == null && id.toString().trim().isEmpty()){
            return Mono.error(new NullPointerException("The values of field \"Id\" don't allow null"));
        }
        return Mono.just(id);
    }

    private Mono<User> UserIsNull(User user)
    {
        if(user == null){
            return Mono.error(new NullPointerException("The User to edit can't be null."));
        }
        return Mono.just(user);
    }

    private Mono<Boolean> validRangeSalary(BigInteger salary)
    {
        return  Mono.just(salary.compareTo( BigInteger.ZERO)  >= 0 && salary.compareTo(BigInteger.valueOf(15000000)) <= 0);
    }
}
