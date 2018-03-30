package com.example.messagequeuetest.demo;

import com.example.messagequeuecommon.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

@SpringBootApplication
@Slf4j
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class DemoApplication implements CommandLineRunner {

    @Autowired
    @Qualifier("personRestTemplate")
    RestTemplate restTemplate;

    private static final int personCount = 100;

    private static final int concurrence = 50;

    private static final int changeTimes = 50;

    private AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoApplication.class).web(false).run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        List<Thread> threadContainer = new ArrayList<>();
        for (int i = 0; i < personCount; i++) {
            Thread thread = new Thread(new PersonTestRunner());
            thread.start();
            threadContainer.add(thread);
        }

        for (Thread thread : threadContainer) {
            thread.join();
        }
        log.info("end..");

    }


    class PersonTestRunner implements Runnable {
        @Override
        public void run() {
            Long id = restTemplate.postForObject("/persons",
                    new Person(UUID.randomUUID().toString(),
                            Integer.valueOf(String.valueOf(Thread.currentThread().getId()).substring(0, 2)),
                            System.currentTimeMillis()), Long.class);
            Person person = restTemplate.getForObject("/persons/" + id, Person.class);
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(concurrence, 100, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
            for (int i = 0; i < changeTimes; i++) {
                threadPoolExecutor.execute(new PostRunner(person));
            }
            log.info("end" + Thread.currentThread().getId());
        }
    }

    class PostRunner implements Runnable {
        Person person;

        public PostRunner(Person person) {
            this.person = person;
        }
        @Override
        public void run() {
            Person newPerson = new Person();
            BeanUtils.copyProperties(person, newPerson);
            newPerson.setName(UUID.randomUUID().toString());
            newPerson.setTime(System.currentTimeMillis());
            restTemplate.put("/persons/" + newPerson.getId(), newPerson);
            System.out.println(
                    atomicInteger.addAndGet(1));
        }
    }

}
