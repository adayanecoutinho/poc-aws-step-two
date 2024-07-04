package br.com.zup.poc_aws_step_two.service;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SQSService {
    private final SqsTemplate sqsTemplate;

    @Autowired
    public SQSService(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendMessage(String queueUrl, String message) {
        sqsTemplate.send(queueUrl, message);
        System.out.println("Mensagem enviada para a fila: " + queueUrl);
    }
}
