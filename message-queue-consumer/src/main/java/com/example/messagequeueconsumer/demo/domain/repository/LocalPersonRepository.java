package com.example.messagequeueconsumer.demo.domain.repository;

import com.example.messagequeueconsumer.demo.domain.model.LocalPerson;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author winters
 * 创建时间：06/12/2017 09:35
 * 创建原因：
 **/
public interface LocalPersonRepository extends JpaRepository<LocalPerson,Long> {
}
