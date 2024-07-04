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
public class KafkaConsumerService {

    private final DynamoDBService dynamoDBService;
    private final SQSService sqsService;

    @Value("${aws.sqs.queue-url}")
    private String queueName;

    @Value("${kafka.consumer.group-id}")
    private final String groupId;

    @Value("${kafka.consumer.topic}")
    private final String topic;

    public KafkaConsumerService(DynamoDBService
                                dynamoDBService, SQSService sqsService,
                                @Value("${kafka.consumer.group-id}") String groupId,
                                @Value("${kafka.consumer.topic}") String topic) {
        this.dynamoDBService = dynamoDBService;
        this.sqsService = sqsService;
        this.groupId = groupId;
        this.topic = topic;
    }

    @KafkaListener(topics = "${kafka.consumer.topic}", groupId = "${kafka.consumer.group-id}")
    public void consume(String message) {
        log.info("Consumed message: {}", message);
        dynamoDBService.saveMessage(message);
        sqsService.sendMessage(queueName, message);
    }
}
