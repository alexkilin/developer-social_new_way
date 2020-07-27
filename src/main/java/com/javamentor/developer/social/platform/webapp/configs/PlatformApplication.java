package com.javamentor.developer.social.platform.webapp.configs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@ComponentScan("com.javamentor.developer.social.platform")
@EntityScan("com.javamentor.developer.social.platform.models.entity")
public class PlatformApplication {

    public static void main(String[] args){
        SpringApplication.run(PlatformApplication.class, args);

    }

}
