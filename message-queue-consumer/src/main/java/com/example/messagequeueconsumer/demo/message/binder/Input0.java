package com.example.messagequeueconsumer.demo.message.binder;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author winters
 * 创建时间：05/12/2017 14:33
 * 创建原因：
 **/
public interface Input0 {

    String INPUT = "input0";

	@Input(Input0.INPUT)
    SubscribableChannel input();
}
