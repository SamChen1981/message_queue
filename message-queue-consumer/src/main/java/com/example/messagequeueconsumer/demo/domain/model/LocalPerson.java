package com.example.messagequeueconsumer.demo.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author winters
 * 创建时间：06/12/2017 10:54
 * 创建原因：
 **/
@Entity
@Data
@Table(name="Person")
public class LocalPerson {
    @Id
    private Long id;

    private String name;

    private int age;

    private Long time;

    //仅存储不做版本号 乐观锁处理
    private int version;
}
