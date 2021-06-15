package com.javamentor.developer.social.platform.restv2.controllers;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



//    @DataSet(value = {
//   //         "datasets/restv2/MyTest/audioResources/Genre.yml",
//   //         "datasets/restv2/MyTest/audioResources/Music_genres.yml"
//    }, strategy = SeedStrategy.REFRESH, cleanAfter = true)
    @Sql(value = "/data_for_AudioLastPlayedTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
    class AudioLastPlayedV2Test extends AbstractIntegrationTest {



        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private EntityManager entityManager;

        Gson gson = new Gson();


        @Test
        void onGetLastPlayedAudio() throws Exception {

            mockMvc.perform(get("/api/v2/users/{userId}/lastPlayedAudio","666"))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/api/v2/users/{userId}/lastPlayedAudio","667"))
                    .andExpect(status().isNotFound());

        }
    }



