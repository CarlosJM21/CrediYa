package co.com.mrcompany.api.mappers;

import co.com.mrcompany.api.dto.request.UserRequestDto;
import co.com.mrcompany.api.dto.response.UserResponseDto;
import co.com.mrcompany.model.user.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-01T13:26:57-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto toResponse(User domain) {
        if ( domain == null ) {
            return null;
        }

        UserResponseDto.UserResponseDtoBuilder userResponseDto = UserResponseDto.builder();

        userResponseDto.id( domain.getId() );
        userResponseDto.name( domain.getName() );
        userResponseDto.lastName( domain.getLastName() );
        userResponseDto.email( domain.getEmail() );
        userResponseDto.dni( domain.getDni() );
        userResponseDto.cellphone( domain.getCellphone() );
        userResponseDto.birthDate( domain.getBirthDate() );
        userResponseDto.address( domain.getAddress() );
        userResponseDto.idRol( domain.getIdRol() );
        userResponseDto.baseSalary( domain.getBaseSalary() );

        return userResponseDto.build();
    }

    @Override
    public User toDomain(UserRequestDto request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( request.getId() );
        user.name( request.getName() );
        user.lastName( request.getLastName() );
        user.email( request.getEmail() );
        user.dni( request.getDni() );
        user.cellphone( request.getCellphone() );
        user.birthDate( request.getBirthDate() );
        user.address( request.getAddress() );
        user.idRol( request.getIdRol() );
        user.baseSalary( request.getBaseSalary() );

        return user.build();
    }
}
