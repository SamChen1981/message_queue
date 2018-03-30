package com.example.messagequeueproducer.demo.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author winters
 * 创建时间：05/12/2017 17:17
 * 创建原因：
 **/
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
//tracker落地 代表已经成功发送一次  已经转储的事件 不再投递到消息队列
@Data
public class MessageSendTracker {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private Long eventId;

}
