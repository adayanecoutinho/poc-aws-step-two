package br.com.zup.poc2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final DynamoDBService dynamoDBService;
    private final SQSService sqsService;

    @KafkaListener(topics = "topicTeste", groupId = "group_teste")
    public void consume(String message) {
        log.info("Consumed message: {}", message);
        dynamoDBService.saveMessage(message);
        sqsService.sendMessage(message);
    }
}
