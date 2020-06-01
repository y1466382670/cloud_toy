package com.yu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CloudToyEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudToyEurekaApplication.class, args);
    }

}
