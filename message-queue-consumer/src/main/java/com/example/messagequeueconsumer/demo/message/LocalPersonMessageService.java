package com.example.messagequeueconsumer.demo.message;

import com.example.messagequeuecommon.model.PersonCreateEvent;
import com.example.messagequeuecommon.model.PersonUpdateEvent;
import org.springframework.messaging.Message;

/**
 * @author winters
 * 创建时间：25/12/2017 19:15
 * 创建原因：
 **/
public interface LocalPersonMessageService {
    void handlePersonUpdateEventMessage(Message<PersonUpdateEvent> msg);

    void handlePersonCreateEventMessage(Message<PersonCreateEvent> msg);
}
