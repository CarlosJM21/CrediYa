package co.com.mrcompany.consumer.mapper;

import co.com.mrcompany.consumer.UserResponse;
import co.com.mrcompany.model.userauth.UserAuth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserAuth toDomain(UserResponse response);
}
