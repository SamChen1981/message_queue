package com.example.messagequeueconsumer.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.messaging.Message;

@SpringBootApplication
@EntityScan("com.example.*")
@Slf4j
public class DemoApplication {

    public static void main(String[] args) {
        String path = "/Users/dengfengqi/kafka_client_jaas.conf";
        System.out.println(path);
        System.setProperty("java.security.auth.login.config", path);
        SpringApplication.run(DemoApplication.class, args);
    }

    //@StreamListener(target = Input0.INPUT)
    public void handleError(Message<?> msg) {
    }
}
