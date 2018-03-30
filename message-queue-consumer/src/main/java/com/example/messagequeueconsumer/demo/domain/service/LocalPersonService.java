package com.example.messagequeueconsumer.demo.domain.service;

import com.example.messagequeuecommon.model.PersonCreateEvent;
import com.example.messagequeuecommon.model.PersonUpdateEvent;

/**
 * @author winters
 * 创建时间：26/12/2017 16:51
 * 创建原因：
 **/
public interface LocalPersonService {

    void handlePersonUpdateEvent(PersonUpdateEvent event);

    void handlePersonCreateEvent(PersonCreateEvent event);
}
