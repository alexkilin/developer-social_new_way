package com.javamentor.developer.social.platform.webapp.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingClass({"org.springframework.boot.test.context.SpringBootTest"})
public class TestDataInitializer implements CommandLineRunner {

/**
*    Здесь мы должны вызвать метод TestDataInit
*/
    @Override
    public void run(String... args) throws Exception {

    }
}
