package co.com.bancolombia.dynamodb.helper;

import co.com.bancolombia.dynamodb.DynamoReportAdapter;
import co.com.bancolombia.dynamodb.ReportEntity;
import co.com.bancolombia.model.Report;
import co.com.bancolombia.model.helpers.Ilogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class TemplateAdapterOperationsTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private DynamoDbAsyncClient dbDynamo;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<ReportEntity> customerTable;

    @Mock
    private Ilogger logger;

    private ReportEntity reportEntity;
    private Report report;
    private String metrica;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        metrica = "Approved";
        var cant = 1L;
        var amount = new BigInteger("500000");

        when(dynamoDbEnhancedAsyncClient.table("table_name", TableSchema.fromBean(ReportEntity.class)))
                .thenReturn(customerTable);

        report =  new Report().toBuilder()
                              .metrica(metrica)
                              .cant(cant)
                              .totalAmount(amount)
                              .build();

        reportEntity = new ReportEntity();
        reportEntity.setMetrica(metrica);
        reportEntity.setCant(cant);
        reportEntity.setAmount(amount );
    }

    @Test
    void modelEntityPropertiesMustNotBeNull() {
        ReportEntity reportEntityUnderTest = new ReportEntity("id", 1L, new BigInteger("500000"));

        assertNotNull(reportEntityUnderTest.getMetrica());
        assertNotNull(reportEntityUnderTest.getCant());
        assertNotNull(reportEntityUnderTest.getAmount());
    }

    @Test
    void testSave() {
        when(customerTable.putItem(reportEntity)).thenReturn(CompletableFuture.runAsync(()->{}));
        when(mapper.map(reportEntity, ReportEntity.class)).thenReturn(reportEntity);

        DynamoReportAdapter dynamoReportAdapter =
                new DynamoReportAdapter(dynamoDbEnhancedAsyncClient,dbDynamo, mapper,logger);

        StepVerifier.create(dynamoReportAdapter.save(report))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testGetById() {
        String id = "id";

        when(customerTable.getItem(
                Key.builder().partitionValue(AttributeValue.builder().s(id).build()).build()))
                .thenReturn(CompletableFuture.completedFuture(reportEntity));
        when(mapper.map(reportEntity, Object.class)).thenReturn("value");

        DynamoReportAdapter dynamoReportAdapter =
                new DynamoReportAdapter(dynamoDbEnhancedAsyncClient,dbDynamo, mapper, logger);

        StepVerifier.create(dynamoReportAdapter.getById(metrica))
                .expectNext()
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(mapper.map(reportEntity, ReportEntity.class)).thenReturn(reportEntity);
        when(mapper.map(reportEntity, Object.class)).thenReturn("value");

        when(customerTable.deleteItem(reportEntity))
                .thenReturn(CompletableFuture.completedFuture(reportEntity));

        DynamoReportAdapter dynamoReportAdapter =
                new DynamoReportAdapter(dynamoDbEnhancedAsyncClient,dbDynamo, mapper, logger);

        StepVerifier.create(dynamoReportAdapter.delete(report))
                .expectNext()
                .verifyComplete();
    }
}