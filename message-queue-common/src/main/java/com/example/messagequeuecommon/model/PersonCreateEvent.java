package com.example.messagequeuecommon.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author winters
 * 创建时间：05/12/2017 16:55
 * 创建原因：
 **/
@Data
@Builder
public class PersonCreateEvent implements DomainEvent {
    private String messageKey;
    /**
     * 根据person的id做分片，这样一个person实例的生命周期内的所有 删除 更新 新增 领域事件
     * 都会被按发生领域事件的先后 顺序投递到一个 partion 里面去 ，
     * 在消费方 一个partition 对应一个FIFO队列 一个单线程的消费者，可以保证顺序消费
     */
    private Person person;

    private long occurredTime;

    @Override
    public Long occurredOn() {
        return occurredTime;
    }

}
