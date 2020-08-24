package com.javamentor.developer.social.platform.webapp.configs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.javamentor.developer.social.platform")
@EntityScan("com.javamentor.developer.social.platform.models.entity")
@EnableSwagger2
public class PlatformApplication {

    public static void main(String[] args){
        SpringApplication.run(PlatformApplication.class, args);

    }

}
