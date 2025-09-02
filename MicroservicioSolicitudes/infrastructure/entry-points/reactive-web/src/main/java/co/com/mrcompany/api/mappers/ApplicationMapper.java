package co.com.mrcompany.api.mappers;

import co.com.mrcompany.api.dtos.ApplicationResponse;
import co.com.mrcompany.api.dtos.applicationRequest;
import co.com.mrcompany.model.application.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplicationMapper {

   // @Mapping( source = "idStatus", target = "idStatus" )
   // @Mapping( source = "idStatus", target = "status" )
    ApplicationResponse toResponse(Application domain);

    @Mapping( source = "typeId", target = "idLoanType" )
    Application toDomain(applicationRequest request);
}
