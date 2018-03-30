package com.example.messagequeueconsumer.demo.message.binder;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author winters
 * 创建时间：05/12/2017 14:34
 * 创建原因：
 **/
public interface Input1 {
    String INPUT = "input1";

	@Input(Input1.INPUT)
    SubscribableChannel input();
}
