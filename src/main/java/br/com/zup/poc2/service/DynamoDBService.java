package br.com.zup.poc2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public void saveMessage(String message) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("messageId", AttributeValue.builder().s("id").build());
        item.put("content", AttributeValue.builder().s(message).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("TesteDB2")
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
        log.info("Message saved to DynamoDB: {}", message);
    }
    }
