package com.example.messagequeueproducer.demo;

import com.github.ltsopensource.spring.boot.annotation.EnableJobClient;
import com.github.ltsopensource.spring.boot.annotation.EnableTaskTracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBinding(Source.class)
@EnableScheduling
@EntityScan("com.example.*")
@EnableJobClient
@EnableTaskTracker
public class DemoApplication {

    public static void main(String[] args) {
        String path = "/Users/dengfengqi/kafka_client_jaas.conf";
        System.out.println(path);
        System.setProperty("java.security.auth.login.config", path);
        SpringApplication.run(DemoApplication.class, args);
    }

}
