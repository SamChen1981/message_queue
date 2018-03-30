package com.example.messagequeueproducer.demo.domain.repository;

import com.example.messagequeuecommon.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author winters
 * 创建时间：05/12/2017 18:21
 * 创建原因：
 **/
public interface PersonRepository extends JpaRepository<Person, Long> {

    /*
    @Query(value = "select * from `Person` order by `Person`.`id` desc limit 50", nativeQuery = true)
    List<Person> findTopNPersonForRandomUpdate();
    */
}
