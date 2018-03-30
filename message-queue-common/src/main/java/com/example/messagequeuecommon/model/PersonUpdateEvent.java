package com.example.messagequeuecommon.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author winters
 * 创建时间：06/12/2017 11:14
 * 创建原因：
 **/
@Data
@Builder
public class PersonUpdateEvent implements DomainEvent {

    private Person person;

    private Long occurredTime;

    private String messageKey;

    @Override
    public Long occurredOn() {
        return occurredTime;
    }
}
