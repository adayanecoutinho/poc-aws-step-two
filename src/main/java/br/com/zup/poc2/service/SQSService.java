package br.com.zup.poc2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class SQSService {

    private final SqsClient sqsClient;

    public void sendMessage(String message) {
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl("https://localhost.localstack.cloud:4566/000000000000/FilaTeste2")
                .messageBody(message)
                .build();

        sqsClient.sendMessage(sendMsgRequest);
        log.info("Message sent to SQS: {}", message);
    }
}
