package com.yu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CloudToyAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudToyAuthApplication.class, args);
    }

}
