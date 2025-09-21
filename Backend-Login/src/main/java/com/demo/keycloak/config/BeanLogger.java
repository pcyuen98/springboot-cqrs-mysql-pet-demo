package com.demo.keycloak.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class BeanLogger implements ApplicationRunner {
    private final ApplicationContext applicationContext;

    public BeanLogger(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("==== Beans from 'com.demo' package ====");

        List<String> beanNames = Arrays.asList(applicationContext.getBeanDefinitionNames());

        List<String> demoBeans = beanNames.stream()
                .filter(beanName -> {
                	
                    Object bean = applicationContext.getBean(beanName);
                    return bean != null && bean.getClass().getPackage() != null &&
                           bean.getClass().getPackage().getName().startsWith("com.demo");
                })
                .sorted()
                .toList();

        demoBeans.forEach(beanName -> {
            Object bean = applicationContext.getBean(beanName);
            log.info("{} -> {}", beanName, bean.getClass().getName());
        });
    }

}