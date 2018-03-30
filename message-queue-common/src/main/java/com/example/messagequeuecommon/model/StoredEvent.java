package com.example.messagequeuecommon.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author winters
 * 创建时间：05/12/2017 17:04
 * 创建原因：
 **/
@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class StoredEvent {

    @Id
    @GeneratedValue
    private Long eventId;

    @NonNull
    private String eventBody;

    @NonNull
    private Long occurredOn;

    @NonNull
    private String typeName;

    @NonNull
    private String messageKey;

}
