package br.com.zup.poc_aws_step_two.config;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class AwsConfig {

    @Value("${aws.url}")
    private String url;

    @Value("${aws.credenciais.access-key}")
    private String accessKey;

    @Value("${aws.credenciais.secret-key}")
    private String secretKey;
    @Bean
    public SqsAsyncClient sqsClient() {
        AwsBasicCredentials awsCreds = getAwsBasicCredentials();
        return SqsAsyncClient.builder()
                .endpointOverride(URI.create(url))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }


    private AwsBasicCredentials getAwsBasicCredentials() {
        return AwsBasicCredentials.create(this.accessKey, this.secretKey);
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        AwsBasicCredentials awsCreds = getAwsBasicCredentials();
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(url))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsClient) {
        return SqsTemplate.newTemplate(sqsClient);
    }
}
