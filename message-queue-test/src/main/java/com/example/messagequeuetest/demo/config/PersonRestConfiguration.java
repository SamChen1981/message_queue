package com.example.messagequeuetest.demo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author winters
 * 创建时间：06/12/2017 13:09
 * 创建原因：
 **/
@Configuration
public class PersonRestConfiguration {
    @Bean
    public RestTemplate personRestTemplate() {
        return new RestTemplateBuilder().rootUri("http://localhost:8088/").messageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

}
