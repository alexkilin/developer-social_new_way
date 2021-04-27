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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DataSet(value = {"datasets/restv2/genre/Genre.yml"},
        strategy = SeedStrategy.REFRESH, cleanAfter = true)
@Sql(value = "/create_user_content.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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

    @DataSet(value = {
            "datasets/restv2/audio/usersResources/Active.yml",
            "datasets/restv2/audio/usersResources/Friends.yml",
            "datasets/restv2/audio/usersResources/User.yml",
            "datasets/restv2/audio/usersResources/Role.yml",
            "datasets/restv2/audio/audioResources/UsersAudiosCollections.yml" ,
            "datasets/restv2/audio/playlistAudioTest/Playlist.yml",
            "datasets/restv2/audio/playlistAudioTest/PlaylistHasAudios.yml",
            "datasets/restv2/audio/audioResources/Media.yml" ,
            "datasets/restv2/audio/albumAudioTest/AudioAlbum.yml",
            "datasets/restv2/audio/albumAudioTest/Album.yml",
            "datasets/restv2/audio/albumAudioTest/UserHasAlbum.yml",
            "datasets/restv2/audio/albumAudioTest/AlbumHasAudio.yml",
            "datasets/restv2/audio/audioResources/Audio.yml",
            "datasets/restv2/audio/audioResources/Genre.yml",
            "datasets/restv2/audio/audioResources/Music_genres.yml"}, strategy = SeedStrategy.REFRESH, cleanAfter = true)
    @Test
    void onDeleteGenre() throws Exception {

        mockMvc.perform(get("/api/v2/audio/by/genre")
                .param("genre", "Rock")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(200))
                .andExpect(jsonPath("$.items[1].id").value(201))
                .andExpect(jsonPath("$.items[2].id").value(202))
                .andExpect(jsonPath("$.items.length()").value(3));

        mockMvc.perform(delete("/api/v2/genres/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));


        mockMvc.perform(get("/api/v2/audio/by/genre")
                .param("genre", "Rock")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/api/v2/genres/6"))
                .andExpect(status().isNotFound());


    }


}
