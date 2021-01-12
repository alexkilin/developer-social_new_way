package com.javamentor.developer.social.platform.restv2.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.developer.social.platform.webapp.configs.PlatformApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import profile.DefaultProfile;

import java.util.TimeZone;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PlatformApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@DefaultProfile
//@TestPropertySource("/config/application-test_local.properties")
@PropertySource(encoding = "UTF-8", value = "/message/application-error-message.properties")
@AutoConfigureMockMvc
@DBRider
@DBUnit(caseSensitiveTableNames = true, allowEmptyFields = true, schema = "public")
public abstract class AbstractIntegrationTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}
