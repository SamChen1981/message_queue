package com.example.messagequeueproducer.demo.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author winters
 * 创建时间：27/12/2017 12:38
 * 创建原因：
 **/
@Service
public class MessageQueueJobServiceImpl {

    private MessageSendService messageSendService;

    @Autowired
    public MessageQueueJobServiceImpl(MessageSendService messageSendService) {
        this.messageSendService = messageSendService;
    }


}
