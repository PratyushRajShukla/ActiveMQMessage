package com.lumenmq.queuemq.Component;


import com.lumenmq.queuemq.model.DbMessage;
import com.lumenmq.queuemq.model.SystemMessage;
import com.lumenmq.queuemq.repository.MessageRepository;
import jakarta.jms.Message;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.util.ByteSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    private MessageRepository messageRepository;

    @JmsListener(destination = "message-queue")
    public void messageListener(Message message) throws InterruptedException {
        LOGGER.info("Message received queue format {} ", message);
        ByteSequence bytes = ((ActiveMQTextMessage) message).getContent();
        byte[] byteArray = bytes.getData();
        byteArray = Arrays.copyOfRange(byteArray, 4, byteArray.length);
        String queueMessageStringFormat = new String(byteArray, StandardCharsets.UTF_8);
        LOGGER.info("Message transformed {} ", queueMessageStringFormat);
        DbMessage dbMessage = new DbMessage(queueMessageStringFormat);
        messageRepository.save(dbMessage);
    }
}
