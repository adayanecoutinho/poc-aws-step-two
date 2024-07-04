package br.com.zup.poc_aws_step_two.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DynamoDBService {

    private final DynamoDbClient dynamoDbClient;

    @Value("${aws.dynamodb.table-name}")
    private String tableName;

    public void saveMessage(String message) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("messageId", AttributeValue.builder().s("id").build());
        item.put("content", AttributeValue.builder().s(message).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
        log.info("Message saved to DynamoDB: {}", message);
    }
    }
