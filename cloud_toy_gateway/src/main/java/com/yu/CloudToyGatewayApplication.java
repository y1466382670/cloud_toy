package com.yu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CloudToyGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudToyGatewayApplication.class, args);
    }

}
