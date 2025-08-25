package co.com.mrcompany.api.mappers;

import co.com.mrcompany.api.Dto.Request.UserRequestDto;
import co.com.mrcompany.api.Dto.Response.UserResponseDto;
import co.com.mrcompany.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponseDto toResponse(User domain);

    @Mapping(target = "DNI", source = "dni")
    User toDomain(UserRequestDto request);
}
