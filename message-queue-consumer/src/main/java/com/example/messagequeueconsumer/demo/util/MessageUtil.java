package com.example.messagequeueconsumer.demo.util;

import org.springframework.messaging.Message;

/**
 * @author winters
 * 创建时间：26/12/2017 16:41
 * 创建原因：
 **/
public class MessageUtil {

    public static String getKafkaMessageId(Message<?> msg) {
        return String.valueOf(
                msg.getHeaders().get("id"));
    }
}
