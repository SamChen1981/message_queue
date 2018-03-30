package com.example.messagequeuecommon.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * @author winters
 * 创建时间：05/12/2017 13:00
 * 创建原因：
 **/
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private int age;
    @NonNull
    private Long time;
    @Version
    private int version;
}
