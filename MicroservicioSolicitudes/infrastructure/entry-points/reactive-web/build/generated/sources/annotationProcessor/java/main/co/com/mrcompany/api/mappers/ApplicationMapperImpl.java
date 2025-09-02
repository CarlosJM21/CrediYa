package co.com.mrcompany.api.mappers;

import co.com.mrcompany.api.dtos.ApplicationResponse;
import co.com.mrcompany.api.dtos.applicationRequest;
import co.com.mrcompany.model.application.Application;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-01T17:15:39-0500",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class ApplicationMapperImpl implements ApplicationMapper {

    @Override
    public ApplicationResponse toResponse(Application domain) {
        if ( domain == null ) {
            return null;
        }

        ApplicationResponse.ApplicationResponseBuilder applicationResponse = ApplicationResponse.builder();

        applicationResponse.id( domain.getId() );
        applicationResponse.amount( domain.getAmount() );
        applicationResponse.term( domain.getTerm() );
        applicationResponse.email( domain.getEmail() );
        applicationResponse.idStatus( domain.getIdStatus() );
        applicationResponse.idLoanType( domain.getIdLoanType() );

        return applicationResponse.build();
    }

    @Override
    public Application toDomain(applicationRequest request) {
        if ( request == null ) {
            return null;
        }

        Application.ApplicationBuilder application = Application.builder();

        application.idLoanType( request.getTypeId() );
        application.amount( request.getAmount() );
        application.term( request.getTerm() );
        application.email( request.getEmail() );

        return application.build();
    }
}
