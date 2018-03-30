package com.example.messagequeueproducer.demo.web;

import com.example.messagequeuecommon.model.Person;
import com.example.messagequeuecommon.model.PersonCreateEvent;
import com.example.messagequeuecommon.model.PersonUpdateEvent;
import com.example.messagequeueproducer.demo.domain.repository.PersonRepository;
import com.example.messagequeueproducer.demo.domain.service.EventStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author winters
 * 创建时间：05/12/2017 17:36
 * 创建原因：
 **/
@RestController
@Slf4j
public class PersonController {

    private final PersonRepository personRepository;
    private final EventStoreService eventStoreService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public PersonController(PersonRepository personRepository,
                            EventStoreService eventStoreService, ApplicationEventPublisher publisher) {
        this.personRepository = personRepository;
        this.eventStoreService = eventStoreService;
        this.publisher = publisher;
    }


    @PostMapping("/persons")
    @Transactional
    public Long create(@RequestBody Person person) {
        Person savedPerson = personRepository.save(person);
        PersonCreateEvent personCreateEvent = PersonCreateEvent.builder()
                .messageKey(person.getId().toString())
                .person(person)
                .occurredTime(System.currentTimeMillis()).build();
        //模型修改 事件转储 同时落地
        publisher.publishEvent(personCreateEvent);
        return savedPerson.getId();
    }

    @PutMapping("/persons/{id}")
    @Transactional
    public void modify(@PathVariable("id") Long id, @RequestBody Person person) {
        Person oldPerson = personRepository.findOne(id);
        if (oldPerson == null) {
            throw new RuntimeException("person 不存在, 是否已经保存");
        }
        BeanUtils.copyProperties(person, oldPerson, "id");
        personRepository.save(oldPerson);
        PersonUpdateEvent personUpdateEvent = PersonUpdateEvent.builder()
                .messageKey(person.getId().toString())
                .person(oldPerson)
                .occurredTime(System.currentTimeMillis()).build();
        publisher.publishEvent(personUpdateEvent);
    }

    @Transactional(readOnly = true)
    @GetMapping("/persons/{id}")
    public Person getPerson(@PathVariable("id") Long id) {
        return personRepository.findOne(id);
    }
}
