package co.com.mrcompany.usecase.user;

import DomainException.EmailExistsException;
import DomainException.WrongSalaryRangeException;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.UUID;

@RequiredArgsConstructor
public class UserUseCase  implements  IUserUseCase{

    private final UserRepository repository;

    @Override
    public Mono<User> create(User user) {
        return validRangeSalary(user)
                .flatMap(this::NotExistsUser)
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
        if(id == null && id.toString().trim().isEmpty()){
            return Mono.error(new NullPointerException("The values of field \"Id\" don't allow null"));
        }
        return Mono.just(id);
    }

    protected Mono<User> UserIsNull(User user)
    {
        if(user == null){
            return Mono.error(new NullPointerException("The User to edit can't be null."));
        }
        return Mono.just(user);
    }

    protected Mono<User> validRangeSalary(User user)
    {
        BigInteger salary = user.getBaseSalary();
        if (salary.compareTo( BigInteger.ZERO)  >= 0 && salary.compareTo(BigInteger.valueOf(15000000)) <= 0)
        {
            return Mono.just(user);
        }
        return Mono.error( new WrongSalaryRangeException());
    }

    protected Mono<User> NotExistsUser(User user)
    {
       return repository.existsByEmail(user.getEmail())
               .flatMap(v ->{
                   if(v){
                       return Mono.error(new EmailExistsException());
                   }
                   return Mono.just(user);
               });
    }
}
