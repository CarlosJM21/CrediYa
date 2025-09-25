package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Report;
import co.com.bancolombia.model.gateways.ReportRepository;
import co.com.bancolombia.model.helpers.Ilogger;
import co.com.bancolombia.model.sqs.Approved;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportUseCaseTest {

    @InjectMocks
    private ReportUseCase reportUseCase;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private Ilogger logger;

    private Approved approved;
    private Report report;

    private String id;
    private BigInteger amount;

    @BeforeEach
    void setUp(){
        //email = "pedro1@yopmail.com";
         id= "422b5cfb-83bb-11f0-9973-ca1e79762f6b";
         amount =new BigInteger("500000");
        var cant = 1;
        //tokenText ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRybzFAeW9wbWFpbC5jb20iLCJ0eXBlIjoiYmFzaWMiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiMSJ9XSwiaWF0IjoxNzU3Mzg3MTA0LCJleHAiOjE3NTczOTA3MDR9.M903Qfrkjktb98x_CAEMfBRNHqD7h-FXASHNKoxOOw0";

        approved = new Approved().toBuilder()
                                 .loanId(id)
                                 .amount(amount)
                                 .build();

        report = new Report().toBuilder()
                            .totalAmount(amount)
                            .metrica("Approved")
                            .cant(cant)
                            .build();
    }

    @Test
    void getReport() {
        when(reportRepository.getReport()).thenReturn(Mono.just(report));

        Mono<Report> result = reportUseCase.getReport();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getTotalAmount().equals(report.getTotalAmount()))
                .verifyComplete();
    }

    @Test
    void AddApproved() {
    //when(reportRepository.AddApproved(anyString(), any(BigInteger.class))).thenReturn(Mono.empty());

        Mono result = reportUseCase.AddApproved(approved);

        StepVerifier.create(result)
                    .expectNextMatches( c -> c == Mono.empty());
    }
}
