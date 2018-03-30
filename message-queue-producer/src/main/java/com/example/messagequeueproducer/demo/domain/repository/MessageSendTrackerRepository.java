package com.example.messagequeueproducer.demo.domain.repository;

import com.example.messagequeueproducer.demo.domain.model.MessageSendTracker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author winters
 * 创建时间：05/12/2017 18:22
 * 创建原因：
 **/
public interface MessageSendTrackerRepository extends JpaRepository<MessageSendTracker,Long>{
}
