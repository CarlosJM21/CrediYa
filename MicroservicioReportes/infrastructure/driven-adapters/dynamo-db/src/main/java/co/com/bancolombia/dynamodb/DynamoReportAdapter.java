package co.com.bancolombia.dynamodb;

import co.com.bancolombia.dynamodb.helper.TemplateAdapterOperations;
import co.com.bancolombia.model.Report;
import co.com.bancolombia.model.gateways.ReportRepository;
import co.com.bancolombia.model.helpers.IJsonConverter;
import co.com.bancolombia.model.helpers.Ilogger;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


@Repository
public class DynamoReportAdapter extends TemplateAdapterOperations<Report, String, ReportEntity> implements ReportRepository {

    private final DynamoDbEnhancedAsyncClient enhanced;
    private final DynamoDbAsyncClient dbDynamo;
    private final IJsonConverter<ReportEntity> reportConverte;

    private static final String index = "Approved";
    private static final String table = "Report1";

    private final Ilogger logger;

    private static Report ReportEmpty = Report.builder()
            .metrica(index)
            .cant(0L)
            .totalAmount(BigInteger.ZERO)
            .build();

    public DynamoReportAdapter(DynamoDbEnhancedAsyncClient connectionFactory , DynamoDbAsyncClient dynamoDb, ObjectMapper mapper, Ilogger logger, IJsonConverter<ReportEntity> reporConvert) {
        super(connectionFactory, mapper, d -> mapper.map(d, Report.class), table);
        this.enhanced = connectionFactory;
        this.logger = logger;
        this.dbDynamo = dynamoDb;
        this.reportConverte = reporConvert;
    }

    public Mono<List<Report>> getEntityBySomeKeys(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return query(queryExpression);
    }

    public Mono<List<Report>> getEntityBySomeKeysByIndex(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return queryByIndex(queryExpression);
    }

    private QueryEnhancedRequest generateQueryExpression(String partitionKey, String sortKey) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionKey).build()))
                .queryConditional(QueryConditional.sortGreaterThanOrEqualTo(Key.builder().sortValue(sortKey).build()))
                .build();
    }

    @Override
    public Mono<Report> getReport() {
        var tableItem = enhanced.table(table, TableSchema.fromBean(ReportEntity.class));
        this.getById("Approved").doOnSuccess(s -> System.out.println(s.getTotalAmount().toString()) );
        return Mono.fromFuture(tableItem.getItem(getRequest()))
                //.map(this::toModel) // OJO ver si convierte cuando es null
                .map(entity -> {   reportConverte.toJson(entity);
                                                    return entity == null ? ReportEmpty
                                                                   : Report.builder()
                                                                            .metrica(entity.getMetrica())
                                                                            .cant(entity.getCant())
                                                                            .totalAmount(entity.getAmount() == null ? BigInteger.ZERO
                                                                                                                    : entity.getAmount())
                                                                            .build();
                })
                .switchIfEmpty(Mono.just(ReportEmpty))
                .doOnError(e -> logger.logginError("GetReport - error", e));

        /*.getById(index)
                   .doOnSuccess( i -> logger.logginInfo("retorno exitoso: "+ reportConverte.toJson(i) ))
                   .doOnError(e -> logger.logginError("GetReport - error", e));*/
    }

    @Override
    public Mono AddApproved(String loanId, BigInteger amount) {

        return  Mono.fromFuture( dbDynamo.updateItem(createUpdateRequest(amount)) )
                    .doOnSuccess(ok -> logger.logginInfo("AddApproved -  cant++ and acumulated {} OK (requestId={})", amount, loanId))
                    .doOnError(e -> logger.logginError("AddApproved - error with loan= {}) ", loanId))
                    .then();
    }
    
    private GetItemEnhancedRequest getRequest(){

        return GetItemEnhancedRequest.builder()
                                     .key( Key.builder()
                                             .partitionValue(index)
                                             .build() )
                                     .build();
    }

    private UpdateItemRequest createUpdateRequest (BigInteger amount){
        var key = Map.of("Metrica", AttributeValue.builder().s(index).build());
        return UpdateItemRequest.builder()
                .tableName(table)
                .key(key)
                .updateExpression("ADD #c :one, #a :inc ")
                .expressionAttributeNames(Map.of(
                        "#c", "Cant",
                        "#a", "TotalAmount"
                ))
                .expressionAttributeValues(Map.of(
                        ":one", AttributeValue.builder().n("1").build(),
                        ":inc", AttributeValue.builder().n(amount.toString()).build() // Revisar si acumula como se debe
                ))
                .build();
    }
}
