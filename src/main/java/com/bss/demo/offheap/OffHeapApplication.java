package com.bss.demo.offheap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties
@PropertySource("file:${ConfigDir}/application.properties")
@EnableAsync
@Slf4j
public class OffHeapApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(OffHeapApplication.class, args);

        log.info("Application Started !!");


    }
}