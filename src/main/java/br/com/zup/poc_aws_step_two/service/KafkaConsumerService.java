package br.com.zup.poc_aws_step_two.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final DynamoDBService dynamoDBService;
    private final SQSService sqsService;

    @Value("${aws.sqs.queue-url}")
    private String queueName;

    @KafkaListener(topics = "topicTeste", groupId = "group_teste")
    public void consume(String message) {
        log.info("Consumed message: {}", message);
        dynamoDBService.saveMessage(message);
        sqsService.sendMessage(queueName, message);
    }
}
