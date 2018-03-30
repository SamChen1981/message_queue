package com.example.messagequeueconsumer.demo.domain.service.impl;

import com.example.messagequeuecommon.model.Person;
import com.example.messagequeuecommon.model.PersonCreateEvent;
import com.example.messagequeuecommon.model.PersonUpdateEvent;
import com.example.messagequeueconsumer.demo.domain.model.LocalPerson;
import com.example.messagequeueconsumer.demo.domain.repository.LocalPersonRepository;
import com.example.messagequeueconsumer.demo.domain.service.LocalPersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author winters
 * 创建时间：26/12/2017 16:51
 * 创建原因：
 **/
@Service
public class LocalPersonServiceImpl implements LocalPersonService {

    private final LocalPersonRepository localPersonRepository;

    @Autowired
    public LocalPersonServiceImpl(LocalPersonRepository localPersonRepository) {
        this.localPersonRepository = localPersonRepository;
    }


    @Override
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handlePersonUpdateEvent(PersonUpdateEvent event) {
        Person person = event.getPerson();
        LocalPerson localPerson = localPersonRepository.findOne(person.getId());
        if (localPerson == null) {
            throw new RuntimeException("本地模型不存在 更新失败,检查是否消息投递顺序存在问题");
        }
        BeanUtils.copyProperties(person, localPerson, "id");
        localPersonRepository.save(localPerson);
    }

    @Override
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handlePersonCreateEvent(PersonCreateEvent event) {
        LocalPerson localPerson = new LocalPerson();
        BeanUtils.copyProperties(event.getPerson(), localPerson);
        localPersonRepository.save(localPerson);
    }
}
