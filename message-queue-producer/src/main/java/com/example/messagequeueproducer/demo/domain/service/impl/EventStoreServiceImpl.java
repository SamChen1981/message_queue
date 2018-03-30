package com.example.messagequeueproducer.demo.domain.service.impl;

import com.example.messagequeuecommon.model.PersonCreateEvent;
import com.example.messagequeuecommon.model.PersonUpdateEvent;
import com.example.messagequeuecommon.model.StoredEvent;
import com.example.messagequeueproducer.demo.domain.repository.EventStoreRepository;
import com.example.messagequeueproducer.demo.domain.service.EventStoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author winters
 * 创建时间：25/12/2017 15:09
 * 创建原因：
 **/
@Service
@Log
public class EventStoreServiceImpl implements EventStoreService {
    private final EventStoreRepository eventStoreRepository;

    private final ObjectMapper objectMapper;


    @Autowired
    public EventStoreServiceImpl(EventStoreRepository eventStoreRepository, ObjectMapper objectMapper) {
        this.eventStoreRepository = eventStoreRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handlePersonUpdateEvent(PersonUpdateEvent event) {
        try {
            StoredEvent storedEvent = new StoredEvent(objectMapper.writeValueAsString(event), event.occurredOn(), event.getClass().getTypeName(), event.getMessageKey());
            eventStoreRepository.save(storedEvent);
        } catch (JsonProcessingException e) {
            log.warning(e.toString());
            throw new RuntimeException("error");
        }
    }

    @Override
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handlePersonCreateEvent(PersonCreateEvent event) {
        try {
            StoredEvent storedEvent = new StoredEvent(objectMapper.writeValueAsString(event), event.occurredOn(), event.getClass().getTypeName(), event.getMessageKey());
            eventStoreRepository.save(storedEvent);
        } catch (JsonProcessingException e) {
            log.warning(e.toString());
            throw new RuntimeException("error");
        }
    }

}
