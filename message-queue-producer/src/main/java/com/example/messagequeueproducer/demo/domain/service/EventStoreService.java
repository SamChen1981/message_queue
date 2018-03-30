package com.example.messagequeueproducer.demo.domain.service;

import com.example.messagequeuecommon.model.PersonCreateEvent;
import com.example.messagequeuecommon.model.PersonUpdateEvent;

/**
 * @author winters
 * 创建时间：25/12/2017 15:08
 * 创建原因：
 **/
public interface EventStoreService {

    void handlePersonUpdateEvent(PersonUpdateEvent event);

    void handlePersonCreateEvent(PersonCreateEvent event);
}
