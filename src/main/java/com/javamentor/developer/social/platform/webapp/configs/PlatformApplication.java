package com.javamentor.developer.social.platform.webapp.configs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@ComponentScan("com.javamentor.developer.social.platform")
@EntityScan("com.javamentor.developer.social.platform.models.entity")
public class PlatformApplication {

    public static void main(String[] args){
        SpringApplication.run(PlatformApplication.class, args);

    }

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

}
