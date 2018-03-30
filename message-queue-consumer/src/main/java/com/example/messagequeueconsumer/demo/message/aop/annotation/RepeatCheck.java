package com.example.messagequeueconsumer.demo.message.aop.annotation;

import java.lang.annotation.*;

/**
 * @author winters
 * 创建时间：26/12/2017 17:07
 * 创建原因：
 **/
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatCheck {
}
