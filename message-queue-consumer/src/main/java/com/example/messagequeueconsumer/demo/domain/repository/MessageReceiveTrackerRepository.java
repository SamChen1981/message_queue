package com.example.messagequeueconsumer.demo.domain.repository;

import com.example.messagequeueconsumer.demo.domain.model.MessageReceiveTracker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author winters
 * 创建时间：06/12/2017 09:28
 * 创建原因：
 **/
public interface MessageReceiveTrackerRepository extends JpaRepository<MessageReceiveTracker,Long> {

    MessageReceiveTracker findOneByKafkaMessageId(String messageId);
}
