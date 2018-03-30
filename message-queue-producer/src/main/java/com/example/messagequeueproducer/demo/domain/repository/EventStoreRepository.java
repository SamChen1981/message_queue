package com.example.messagequeueproducer.demo.domain.repository;

import com.example.messagequeuecommon.model.StoredEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

/**
 * @author winters
 * 创建时间：05/12/2017 16:44
 * 创建原因：
 **/
public interface EventStoreRepository extends JpaRepository<StoredEvent,Long> {
//
//    @Query(value = "select * from `StoredEvent` as se left join `MessageSendTracker` as ms " +
//            "on se.`eventId` = ms.`eventId` where ms.`eventId` is null  order by se.`eventId` limit 100",
//            nativeQuery = true)
//    /**
//     * 找出未发送成功的事件
//     * 根据领域事件发起时间进行排序
//     *
//     * 分布式服务的时候 ,时间可能不会保持一致
//     * 此时以mysql单库单表的自增主键为序
//     */
//    List<StoredEvent> getNotSendEventStoredOrderByOccurredTime();

    @Query(value = "select * from `StoredEvent` as se where se.`eventId` > ? order by eventId limit ? " ,
            nativeQuery = true)
    List<StoredEvent> getEventStoredIdThan(Long eventId , int limit);
}
