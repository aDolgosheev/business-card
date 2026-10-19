package ru.dolgosheev.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BusinessCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessCardApplication.class, args);
    }
}
