package co.com.mrcompany.api.mappers;

import co.com.mrcompany.api.Dto.Request.UserRequestDto;
import co.com.mrcompany.api.Dto.Response.UserResponseDto;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.r2dbc.Entities.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toResponse(User domain);

    User toDomain(UserRequestDto record);

    UserResponseDto toDto(User domain);

    UserEntity toEntity(User domain);

    List<User> toDomainList(List<UserEntity> entities);

    List<UserEntity> toEntityList(List<User> domains);

    List<UserResponseDto> toResponseList(List<User> domains);

}
