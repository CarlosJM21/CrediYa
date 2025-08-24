package co.com.mrcompany.api.mappers;

import co.com.mrcompany.api.Dto.Request.UserRequestDto;
import co.com.mrcompany.api.Dto.Response.UserResponseDto;
import co.com.mrcompany.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toResponse(User domain);

    User toDomain(UserRequestDto requets);
}
