package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.UUID;

@RequiredArgsConstructor
public class UserUseCase  implements  IUserUseCase{

    private final UserRepository repository;

    @Override
    public Mono<User> create(User user) {

        if (anyPropertyIsNull(user)) {
            return Mono.error(new IllegalArgumentException("Required fields are missing"));
        }

        if ( validRangeSalary(user.getBaseSalary())) {
            return Mono.error(new IllegalArgumentException("The values of field \"BaseSalary\" must be between 0 and 1500000"));
        }

        repository.findByEmail(user.getEmail()).switchIfEmpty( Mono.error(new IllegalArgumentException("Email already exists")));

        return repository.save(user);
    }

    private boolean anyPropertyIsNull(User user){
       return  Arrays.stream(user.getClass().getFields()).anyMatch(f -> f == null);
    }


    private boolean validRangeSalary(BigInteger salary)
    {
        return  salary.compareTo( BigInteger.ZERO)  >= 0 && salary.compareTo(BigInteger.valueOf(15000000)) <= 0;
    }

    @Override
    public Flux<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<User> findById(UUID id) {
        if(id == null && id.toString().trim().isEmpty()){
            return Mono.error(new NullPointerException("The values of field \"Id\" don't allow null"));
        }
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
        if(id == null && id.toString().trim().isEmpty()){
            return Mono.error(new NullPointerException("The values of field \"Id\" don't allow null"));
        }
        return repository.delete(id);
    }
}
