package co.com.mrcompany.model.user.gateways;

import co.com.mrcompany.model.user.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID>{
    @Query("SELECT * FROM products WHERE name ILIKE :searchTerm OFFSET :offset LIMIT :limit")
    Mono<User> findById(UUID id, int offset, int limit);
}
