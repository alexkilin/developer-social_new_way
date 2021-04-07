package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.media.music.GenreDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.GenreDtoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {
        "datasets/restv2/genre/Genre.yml" }
        , strategy = SeedStrategy.REFRESH, cleanAfter = true)
@Sql(value = "/create_user_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
public class GenreControllerV2Test  extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GenreDtoService genreDtoService;

    @Autowired
    EntityManager entityManager;

    private final String apiUrl = "/api/v2/genres";

    private final Gson gson = new Gson();

    @Test
    void addGenre() throws Exception {
        GenreDto genreDto = GenreDto.builder()
                .title("TEST CATEGORY")
                .build();

        MockHttpServletResponse response = mockMvc.perform(post(apiUrl + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(genreDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertNotNull(response.getContentAsString());
        GenreDto responseContent = gson.fromJson(response.getContentAsString() , GenreDto.class);


        Optional<GenreDto> checkDB = genreDtoService.getGenreByTitle("TEST CATEGORY");
        assertTrue(checkDB.isPresent());
        assertEquals(checkDB.get(),responseContent);
    }
}
