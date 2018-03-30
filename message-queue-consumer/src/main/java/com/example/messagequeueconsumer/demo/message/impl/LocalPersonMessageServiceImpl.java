package com.example.messagequeueconsumer.demo.message.impl;

import com.example.messagequeuecommon.model.PersonCreateEvent;
import com.example.messagequeuecommon.model.PersonUpdateEvent;
import com.example.messagequeueconsumer.demo.domain.model.MessageReceiveTracker;
import com.example.messagequeueconsumer.demo.domain.repository.MessageReceiveTrackerRepository;
import com.example.messagequeueconsumer.demo.domain.service.LocalPersonService;
import com.example.messagequeueconsumer.demo.message.LocalPersonMessageService;
import com.example.messagequeueconsumer.demo.message.binder.Input0;
import com.example.messagequeueconsumer.demo.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.messagequeueconsumer.demo.util.MessageUtil.getKafkaMessageId;

/**
 * @author winters
 * 创建时间：25/12/2017 19:16
 * 创建原因：
 **/
@Service
@EnableBinding({Input0.class})
public class LocalPersonMessageServiceImpl implements LocalPersonMessageService {


    private final MessageReceiveTrackerRepository messageReceiveTrackerRepository;
    private final LocalPersonService localPersonService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public LocalPersonMessageServiceImpl(MessageReceiveTrackerRepository messageReceiveTrackerRepository, LocalPersonService localPersonService, ApplicationEventPublisher publisher) {
        this.messageReceiveTrackerRepository = messageReceiveTrackerRepository;
        this.localPersonService = localPersonService;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    @StreamListener(target = Input0.INPUT, condition = "headers['type']=='com.example.messagequeuecommon.model.PersonUpdateEvent'")
    public void handlePersonUpdateEventMessage(Message<PersonUpdateEvent> msg) {
        if (repeatConsume(msg)){
            return;
        }
        messageReceiveTrackerRepository.save(new MessageReceiveTracker(getKafkaMessageId(msg)));
        publisher.publishEvent(msg.getPayload());
    }

    @Override
    @Transactional
    @StreamListener(target = Input0.INPUT, condition = "headers['type']=='com.example.messagequeuecommon.model.PersonCreateEvent'")
    public void handlePersonCreateEventMessage(Message<PersonCreateEvent> msg) {
        if (repeatConsume(msg)){
            return;
        }
        //记录消息id 避免重复消费
        messageReceiveTrackerRepository.save(new MessageReceiveTracker(getKafkaMessageId(msg)));
        publisher.publishEvent(msg.getPayload());
    }

    private boolean repeatConsume(Message msg) {
        if (messageReceiveTrackerRepository.findOneByKafkaMessageId(MessageUtil.getKafkaMessageId(msg)) != null) {
            return true;
        } else {
            return false;
        }
    }

    //@StreamListener(target = Input0.INPUT)
    public void consumerErrorMessage(@Payload String msg, @Headers Map<String, Object> headers) {
        System.out.println(msg);
        System.out.println(headers);
    }


}
