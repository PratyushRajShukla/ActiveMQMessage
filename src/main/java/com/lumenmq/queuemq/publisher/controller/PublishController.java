package com.lumenmq.queuemq.publisher.controller;

import com.lumenmq.queuemq.model.DbMessage;
import com.lumenmq.queuemq.model.SystemMessage;
import com.lumenmq.queuemq.model.SystemMessage;
import com.lumenmq.queuemq.Component.MessageConsumer;
import com.lumenmq.queuemq.repository.MessageRepository;
import jakarta.jms.JMSException;
import jakarta.jms.Message;

import jakarta.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class PublishController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublishController.class);


    @Autowired
    private MessageRepository repository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/publishmessage/queue")
    public ResponseEntity<String> publishMessageToQueue(@RequestBody SystemMessage systemMessage) {
        try {
            jmsTemplate.convertAndSend("message-queue", systemMessage.toString());
            return new ResponseEntity<>("Sent to queue", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/publishmessage/database")
    public ResponseEntity<String> publishMessageToDB(@RequestBody SystemMessage systemMessage) {
        try {

            DbMessage dbMessage = new DbMessage(systemMessage.getMessage());
            messageRepository.save(dbMessage);
            LOGGER.info("Message Sent to DB {} ", dbMessage);
            return new ResponseEntity<>("Sent to DB", HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
