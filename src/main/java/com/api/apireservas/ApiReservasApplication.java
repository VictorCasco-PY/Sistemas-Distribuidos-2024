package com.api.apireservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication

public class ApiReservasApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiReservasApplication.class, args);
    }

}
