package co.com.mrcompany.consumer.mapper;

import co.com.mrcompany.consumer.dtos.TokenRequest;
import co.com.mrcompany.model.token.Token;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface tokenMapper {

    TokenRequest toRequest(Token domain);

}
