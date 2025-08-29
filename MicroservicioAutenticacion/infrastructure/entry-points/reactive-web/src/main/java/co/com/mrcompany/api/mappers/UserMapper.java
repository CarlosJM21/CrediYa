package co.com.mrcompany.api.mappers;

import co.com.mrcompany.api.dto.request.UserRequestDto;
import co.com.mrcompany.api.dto.response.UserResponseDto;
import co.com.mrcompany.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponseDto toResponse(User domain);

    User toDomain(UserRequestDto request);
}
