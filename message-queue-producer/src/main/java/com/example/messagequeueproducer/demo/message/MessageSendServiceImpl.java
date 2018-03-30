package com.example.messagequeueproducer.demo.message;

import com.example.messagequeuecommon.model.StoredEvent;
import com.example.messagequeueproducer.demo.domain.model.MessageSendTracker;
import com.example.messagequeueproducer.demo.domain.repository.EventStoreRepository;
import com.example.messagequeueproducer.demo.domain.repository.MessageSendTrackerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.*;

/**
 * @author winters
 * 创建时间：05/12/2017 18:35
 * 创建原因：
 **/
@Service
public class MessageSendServiceImpl implements MessageSendService{

    private final ObjectMapper objectMapper;

    private final EventStoreRepository eventStoreRepository;

    private final MessageSendTrackerRepository messageSendTrackerRepository;

    private final Source source;

    private final AtomicInteger atomicInteger = new AtomicInteger();

    /*
     * 指定记录 当前已经发送的StoredEventId存放
     */
    private final Long id = 1l;

    @Autowired
    public MessageSendServiceImpl(ObjectMapper objectMapper, EventStoreRepository eventStoreRepository,
                                  MessageSendTrackerRepository messageSendTrackerRepository, Source source) {
        this.objectMapper = objectMapper;
        this.eventStoreRepository = eventStoreRepository;
        this.messageSendTrackerRepository = messageSendTrackerRepository;
        this.source = source;
    }

    @Transactional
    /**
     * 此处只要抛出异常 则回滚所有事物 保证所有转存的领域事件 至少被投递一次
     * 此处可能存在重复投递的情况，由消费者去重
     * 投递可以根据分区值 进行多线程投递 事件投递保证分区有序即可
     * 出现异常 则重新投递
     */
    public void sendMessage() {
        MessageSendTracker messageSendTracker = messageSendTrackerRepository.findOne(id);
        List<StoredEvent> storedEvents = eventStoreRepository.getEventStoredIdThan(messageSendTracker.getEventId(), 100);
        for (StoredEvent storedEvent : storedEvents) {
            Map<String, Object> headers = new HashMap<>();
            headers.put("contentType", "application/json");
            headers.put("type", storedEvent.getTypeName());
            headers.put(KafkaHeaders.MESSAGE_KEY, storedEvent.getMessageKey().getBytes());
            source.output().send(MessageBuilder.createMessage(storedEvent.getEventBody(), new MessageHeaders(headers)));
        }
        if (storedEvents.size() > 0) {
            messageSendTrackerRepository.save(new MessageSendTracker(id, storedEvents.get(storedEvents.size() - 1).getEventId()));
        }
        System.out.println("send total size:" +
                atomicInteger.addAndGet(storedEvents.size()));
    }

}
