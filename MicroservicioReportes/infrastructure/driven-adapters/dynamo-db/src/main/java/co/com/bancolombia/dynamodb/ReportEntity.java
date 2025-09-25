package co.com.bancolombia.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigInteger;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/

@DynamoDbBean
public class ReportEntity {

    private String metrica;
    private long cant;
    private BigInteger totalAmount;

    public ReportEntity() {
    }

    public ReportEntity(String metrica, long cant, BigInteger totalAmount) {
        this.metrica = metrica;
        this.cant = cant;
        this.totalAmount = totalAmount;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("Metrica")
    public String getMetrica() {
        return metrica;
    }

    public void setMetrica(String id) {
        this.metrica = id;
    }

    @DynamoDbAttribute("Cant")
    public Long getCant() {
        return cant;
    }

    public void setCant(Long cant) {
        this.cant = cant;
    }

    @DynamoDbAttribute("TotalAmount")
    public BigInteger getAmount() {
        return totalAmount;
    }

    public void setAmount(BigInteger amount) {
        this.totalAmount = amount;
    }
}
