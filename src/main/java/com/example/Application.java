package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;

/** Main Spring Boot application */
@SpringBootApplication
public class Application {

    /** Starts the application */
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
    
    /** Runs after startup and logs a message */
    @PostConstruct
    public void init()
    {
        Logger log = LoggerFactory.getLogger(Application.class);
        log.info("Java app started");
    }

    /** Simple status check */
    public String getStatus() {
        return "OK";
    }
}
