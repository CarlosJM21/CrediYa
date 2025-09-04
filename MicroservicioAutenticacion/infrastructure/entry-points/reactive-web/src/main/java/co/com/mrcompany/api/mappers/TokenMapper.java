package co.com.mrcompany.api.mappers;

import co.com.mrcompany.api.dto.request.TokenDto;
import co.com.mrcompany.api.dto.response.TokenResponse;
import co.com.mrcompany.model.user.Token;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TokenMapper {
    Token toDomain(TokenDto request);

    TokenResponse toResponse(Token domain);
}
