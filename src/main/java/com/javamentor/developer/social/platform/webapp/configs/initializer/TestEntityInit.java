package com.javamentor.developer.social.platform.webapp.configs.initializer;

import com.javamentor.developer.social.platform.service.impl.TestDataInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingClass({"org.springframework.boot.test.context.SpringBootTest"})
public class TestEntityInit implements CommandLineRunner {
    @Autowired
    private TestDataInitService testDataInitService;


//    public TestEntityInit(TestDataInitService testDataInitService) {
//        this.testDataInitService = testDataInitService;
//    }

    @Override
    public void run(String... args) {
        testDataInitService.createEntity();
    }
}
