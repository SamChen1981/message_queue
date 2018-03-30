package com.example.messagequeuecommon.model;

/**
 * @author winters
 * 创建时间：05/12/2017 16:56
 * 创建原因：
 **/
public interface DomainEvent {
    Long occurredOn();

    String getMessageKey();
}
