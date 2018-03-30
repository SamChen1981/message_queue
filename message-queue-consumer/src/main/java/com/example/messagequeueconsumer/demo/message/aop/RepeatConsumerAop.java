package com.example.messagequeueconsumer.demo.message.aop;

import com.example.messagequeueconsumer.demo.domain.repository.MessageReceiveTrackerRepository;
import com.example.messagequeueconsumer.demo.util.MessageUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author winters
 * 创建时间：26/12/2017 17:01
 * 创建原因：
 **/
@Component
@Aspect
public class RepeatConsumerAop {

    private final MessageReceiveTrackerRepository messageReceiveTrackerRepository;

    @Autowired
    public RepeatConsumerAop(MessageReceiveTrackerRepository messageReceiveTrackerRepository) {
        this.messageReceiveTrackerRepository = messageReceiveTrackerRepository;
    }

    @Around("@annotation(com.example.messagequeueconsumer.demo.message.aop.annotation.RepeatCheck)")
    public Object checkRepeatMessage(ProceedingJoinPoint pjp) throws Throwable {
        String messageId = null;
        for (Object obj : pjp.getArgs()) {
            if (obj instanceof Message) {
                messageId = MessageUtil.getKafkaMessageId((Message) obj);
                break;
            }
        }
        if (messageReceiveTrackerRepository.findOneByKafkaMessageId(messageId)!=null){
            return null;
        }
        return pjp.proceed();
    }


}
