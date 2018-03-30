package com.example.messagequeueconsumer.demo.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author winters
 * 创建时间：06/12/2017 09:26
 * 创建原因：
 **/
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class MessageReceiveTracker {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String kafkaMessageId;
}
