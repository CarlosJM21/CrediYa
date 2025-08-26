package co.com.mrcompany.api.mappers;

import co.com.mrcompany.api.Dto.Request.UserRequestDto;
import co.com.mrcompany.api.Dto.Response.UserResponseDto;
import co.com.mrcompany.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "idRol", source = "id_rol")
    UserResponseDto toResponse(User domain);

    @Mapping(target = "id_rol", source = "idRol")
    @Mapping(target = "DNI", source = "dni")
    User toDomain(UserRequestDto request);
}
